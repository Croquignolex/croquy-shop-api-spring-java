package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.helpers.ErrorMessagesHelper;
import com.shop.croquy.v1.repositories.CountryPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.CountryRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.interfaces.ICountriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService implements ICountriesService {
    private final CountryPagingAndSortingRepository countryPagingAndSortingRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Country> getPaginatedCountries(int pageNumber, int pageSize, String needle) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return countryPagingAndSortingRepository.findAllByNameContainsOrPhoneCodeContainsOrCreatorIsIn(needle, needle, users, pageable);
        }

        return countryPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_ID_NOT_FOUND + id));
    }

    @Override
    public void storeCountryWithCreator(CountryStoreRequest request, String creatorUsername) {
        if(countryRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_NAME_ALREADY_EXIST + request.getName());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);
        countryRepository.save(request.toCountry(creator));
    }

    @Override
    public void updateCountryById(CountryUpdateRequest request, String id) {
        if(countryRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_NAME_ALREADY_EXIST + request.getName());
        }

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_ID_NOT_FOUND + id));

        country.setName(request.getName());
        country.setPhoneCode(request.getPhoneCode());
        country.setDescription(request.getDescription());

        countryRepository.save(country);
    }

    @Override
    public void destroyById(String id) {
        if(countryRepository.findById(id).isEmpty()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_ID_NOT_FOUND + id);
        }

        countryRepository.deleteById(id);
    }

    @Override
    public void toggleStatusById(String id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.COUNTRY_ID_NOT_FOUND + id));

        country.setEnabled(!country.getEnabled());

        countryRepository.save(country);
    }
}