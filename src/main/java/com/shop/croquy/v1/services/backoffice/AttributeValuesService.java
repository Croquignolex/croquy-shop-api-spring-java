package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueUpdateRequest;
import com.shop.croquy.v1.entities.AttributeValue;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.repositories.AttributeValuePagingAndSortingRepository;
import com.shop.croquy.v1.repositories.AttributeValueRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.backoffice.interfaces.IAttributeValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttributeValuesService implements IAttributeValuesService {
    private final AttributeValuePagingAndSortingRepository attributeValuePagingAndSortingRepository;  
    private final AttributeValueRepository attributeValueRepository; 
    private final UserRepository userRepository;
  
    @Override
    public Page<AttributeValue> getPaginatedAttributeValues(int pageNumber, int pageSize, String needle, String sort, String direction) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        return (StringUtils.isNotEmpty(needle))
                ? attributeValuePagingAndSortingRepository.findAllByNameContainsOrValueContains(needle, needle, pageable)
                : attributeValuePagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public AttributeValue getAttributeValueById(String id) {
        return attributeValueRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_VALUE_NOT_FOUND));
    }

    @Override
    public void storeAttributeValueWithCreator(AttributeValueStoreRequest request, String creatorUsername) {
        if(attributeValueRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_VALUE_NAME_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        attributeValueRepository.save(request.toAttributeValue(creator));
    }

    @Override
    public void updateAttributeValueById(AttributeValueUpdateRequest request, String id) {
        if(attributeValueRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_VALUE_NAME_ALREADY_EXIST);
        }

        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_VALUE_NOT_FOUND));

        attributeValue.setName(request.getName());
        attributeValue.setValue(request.getValue());
        attributeValue.setDescription(request.getDescription());

        attributeValueRepository.save(attributeValue);
    }

    @Override
    public void toggleAttributeValueStatusById(String id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_VALUE_NOT_FOUND));

        attributeValue.setEnabled(!attributeValue.getEnabled());

        attributeValueRepository.save(attributeValue);
    }

    @Override
    public void destroyAttributeValueById(String id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_VALUE_NOT_FOUND));

        if(attributeValue.isNonDeletable()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_VALUE_CAN_NOT_BE_DELETED);
        }
 
        attributeValueRepository.deleteById(id);
    } 
}