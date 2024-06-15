package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.state.StateStoreRequest;
import com.shop.croquy.v1.dto.backoffice.state.StateUpdateRequest;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.backoffice.interfaces.IStatesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.STATE_NAME_ALREADY_EXIST;
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
    public Page<State> getPaginatedStates(int pageNumber, int pageSize, String needle, String sort, String direction) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        return (StringUtils.isNotEmpty(needle))
                ? statePagingAndSortingRepository.findAllByNameContains(needle, pageable)
                : statePagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public List<State> getAllEnabledStatesOrderByName() {
        return stateRepository.findByEnabledOrderByName(true);
    }

    @Override
    public State getStateById(String id) {
        return stateRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(STATE_NOT_FOUND));
    }

    @Override
    public void storeStateWithCountryAndCreator(StateStoreRequest request, String creatorUsername) {
        var country = countryRepository.findById(request.getCountryId()).orElse(null);

        if(stateRepository.findFistByNameAndCountry(request.getName(), country).isPresent()) {
            throw new DataIntegrityViolationException(STATE_NAME_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        stateRepository.save(request.toState(country, creator));
    }

    @Override
    public void updateStateById(StateUpdateRequest request, String id) {
        var country = countryRepository.findById(request.getCountryId()).orElse(null);

        if(stateRepository.findFistByNameAndIdNotAndCountry(request.getName(), id, country).isPresent()) {
            throw new DataIntegrityViolationException(STATE_NAME_ALREADY_EXIST);
        }

        State state = stateRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(STATE_NOT_FOUND));

        state.setCountry(country);
        state.setName(request.getName());
        state.setDescription(request.getDescription());

        stateRepository.save(state);
    }

    @Override
    public void toggleStateStatusById(String id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(STATE_NOT_FOUND));

        state.setEnabled(!state.getEnabled());

        stateRepository.save(state);
    }

    @Override
    public void destroyStateById(String id) {
        if(stateRepository.findById(id).isEmpty()) {
            throw new DataIntegrityViolationException(STATE_NOT_FOUND);
        }

        stateRepository.deleteById(id);
    }
}