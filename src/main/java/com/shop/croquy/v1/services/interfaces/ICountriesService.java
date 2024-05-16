package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.country.CountryStateStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.media.CountryFlag;
import com.shop.croquy.v1.entities.State;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICountriesService {
    Page<Country> getPaginatedCountries(int pageNumber, int pageSize, String needle);
    List<Country> getAllEnabledCountriesOrderByName();
    Country getCountryById(String id);
    void storeCountryWithCreator(CountryStoreRequest request, String creatorUsername);
    void updateCountryById(CountryUpdateRequest request, String id);
    CountryFlag changeCountryFlagById(MultipartFile image, String id, String creatorUsername);
    void toggleCountryStatusById(String id);
    void destroyCountryById(String id);
    void destroyCountryFlagById(String id);
    Page<State> getPaginatedStatesByCountryId(int pageNumber, int pageSize, String needle, String id);
    void addStateWithCreator(CountryStateStoreRequest request, String id, String creatorUsername);
}
