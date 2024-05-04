package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.state.StateStoreRequest;
import com.shop.croquy.v1.entities.State;

import org.springframework.data.domain.Page;

public interface IStatesService {
    Page<State> getPaginatedStates(int pageNumber, int pageSize, String needle);
    State getStateById(String id);
    void storeStateWithCountryAndCreator(StateStoreRequest request, String creatorUsername);
//    void updateStateById(CountryUpdateRequest request, String id);
//    void toggleStateStatusById(String id);
    void destroyStateById(String id);
}
