package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import org.springframework.data.domain.Page;

public interface ICountriesService {
    Page<Country> getPaginatedCountries(int pageNumber, int pageSize, String needle);
    Country getCountryById(String id);
    void storeCountryWithCreator(CountryStoreRequest request, String creatorUsername);
    void updateCountryById(CountryUpdateRequest request, String id);
    void destroyById(String id);
    void toggleStatusById(String id);
}
