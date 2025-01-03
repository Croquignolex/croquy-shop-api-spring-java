package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.media.CountryFlag;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.helpers.ImageOptimisationHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.backoffice.interfaces.ICountriesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService implements ICountriesService {
    private final CountryPagingAndSortingRepository countryPagingAndSortingRepository;
    private final StatePagingAndSortingRepository statePagingAndSortingRepository;
    private final CountryRepository countryRepository;
    private final CountryFlagRepository countryFlagRepository;
    private final UserRepository userRepository;

    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @Override
    public Page<Country> getPaginatedCountries(int pageNumber, int pageSize, String needle, String sort, String direction) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        return (StringUtils.isNotEmpty(needle))
                ? countryPagingAndSortingRepository.findAllByNameContainsOrPhoneCodeContains(needle, needle, pageable)
                : countryPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public List<Country> getAllEnabledCountriesOrderByName() {
        return countryRepository.findByEnabledOrderByName(true);
    }

    @Override
    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));
    }

    @Override
    public void storeCountryWithCreator(CountryStoreRequest request, String creatorUsername) {
        if(countryRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(COUNTRY_NAME_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

       countryRepository.save(request.toCountry(creator));
    }

    @Override
    public void updateCountryById(CountryUpdateRequest request, String id) {
        if(countryRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(COUNTRY_NAME_ALREADY_EXIST);
        }

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        country.setName(request.getName());
        country.setPhoneCode(request.getPhoneCode());
        country.setDescription(request.getDescription());

        countryRepository.save(country);
    }

    @Override
    @Transactional
    public void destroyCountryById(String id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        if(country.isNonDeletable()) {
            throw new DataIntegrityViolationException(COUNTRY_CAN_NOT_BE_DELETED);
        }

        if(country.getFlag() != null) {
            ImageOptimisationHelper.deleteFile(country.getFlag().getPath(), mediaFolderPath);
            countryFlagRepository.delete(country.getFlag());
        }

        countryRepository.deleteById(id);
    }

    @Override
    public void toggleCountryStatusById(String id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        country.setEnabled(!country.getEnabled());

        countryRepository.save(country);
    }

    @Override
    public CountryFlag changeCountryFlagById(MultipartFile image, String id, String creatorUsername) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        CountryFlag countryFlag = country.getFlag();

        if(countryFlag != null) ImageOptimisationHelper.deleteFile(country.getFlag().getPath(), mediaFolderPath);
        else countryFlag = new CountryFlag();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        countryFlag.setSize(image.getSize());
        countryFlag.setOriginalName(savedFileDic.get("name"));
        countryFlag.setPath(savedFileDic.get("path"));
        countryFlag.setCountry(country);
        countryFlag.setCreator(creator);

        countryFlagRepository.save(countryFlag);

        return countryFlag;
    }

    @Override
    public void destroyCountryFlagById(String id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        if(country.getFlag() != null) {
            ImageOptimisationHelper.deleteFile(country.getFlag().getPath(), mediaFolderPath);
            countryFlagRepository.delete(country.getFlag());
        } else {
            throw new DataIntegrityViolationException(FLAG_NOT_FOUND);
        }
    }

    @Override
    public Page<State> getPaginatedStatesByCountryId(int pageNumber, int pageSize, String needle, String sort, String direction, String id) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return statePagingAndSortingRepository.findAllByNameContainsOrCreatorIsInAndCountryId(needle, users, id, pageable);
        }

        return statePagingAndSortingRepository.findAllByCountryId(id, pageable);
    }
}