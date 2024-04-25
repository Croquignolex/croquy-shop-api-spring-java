package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.interfaces.IStatesService;

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

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.STATE_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatesService implements IStatesService {
    private final StatePagingAndSortingRepository statePagingAndSortingRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    @Override
    public Page<State> getPaginatedStates(int pageNumber, int pageSize, String needle) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);
            List<Country> countries = countryRepository.findByNameContains(needle);

            return statePagingAndSortingRepository.findAllByNameContainsOrCountryIsInOrCreatorIsIn(needle, countries, users, pageable);
        }

        return statePagingAndSortingRepository.findAll(pageable);
    }

//    @Override
//    public Country getCountryById(String id) {
//        return countryRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));
//    }
//
//    @Override
//    public void storeCountryWithCreator(CountryStoreRequest request, String creatorUsername) {
//        if(countryRepository.findFistByName(request.getName()).isPresent()) {
//            throw new DataIntegrityViolationException(COUNTRY_NAME_ALREADY_EXIST + request.getName());
//        }
//
//        var creator = userRepository.findByUsername(creatorUsername).orElse(null);
//        countryRepository.save(request.toCountry(creator));
//    }
//
//    @Override
//    public void updateCountryById(CountryUpdateRequest request, String id) {
//        if(countryRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
//            throw new DataIntegrityViolationException(COUNTRY_NAME_ALREADY_EXIST + request.getName());
//        }
//
//        Country country = countryRepository.findById(id)
//                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));
//
//        country.setName(request.getName());
//        country.setPhoneCode(request.getPhoneCode());
//        country.setDescription(request.getDescription());
//
//        countryRepository.save(country);
//    }
//
//    @Override
//    public void toggleStatusById(String id) {
//        Country country = countryRepository.findById(id)
//                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));
//
//        country.setEnabled(!country.getEnabled());
//
//        countryRepository.save(country);
//    }

    @Override
    public void destroyStateById(String id) {
        if(stateRepository.findById(id).isEmpty()) {
            throw new DataIntegrityViolationException(STATE_NOT_FOUND);
        }

        stateRepository.deleteById(id);
    }
}