package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.attribute.AttributeStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attribute.AttributeUpdateRequest;
import com.shop.croquy.v1.entities.Attribute;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.enums.AttributeType;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.backoffice.interfaces.IAttributesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttributesService implements IAttributesService {
    private final AttributePagingAndSortingRepository attributePagingAndSortingRepository;  
    private final AttributeRepository attributeRepository; 
    private final UserRepository userRepository;
  
    @Override
    public Page<Attribute> getPaginatedAttributes(int pageNumber, int pageSize, String needle) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize);

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return attributePagingAndSortingRepository.findAllByNameContainsOrCreatorIsIn(needle, users, pageable);
        }

        return attributePagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Attribute getAttributeById(String id) {
        return attributeRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_NOT_FOUND));
    }

    @Override
    public void storeAttributeCreator(AttributeStoreRequest request, String creatorUsername) {
        if(attributeRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_NAME_ALREADY_EXIST + request.getName());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        attributeRepository.save(request.toAttribute(creator));
    }

    @Override
    public void updateAttributeById(AttributeUpdateRequest request, String id) {
        if(attributeRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_NAME_ALREADY_EXIST + request.getName());
        }

        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_NOT_FOUND));

        attribute.setName(request.getName());
        attribute.setType(AttributeType.getEnumFromString(request.getType()));
        attribute.setDescription(request.getDescription());

        attributeRepository.save(attribute);
    }

    @Override
    public void toggleAttributeStatusById(String id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_NOT_FOUND));

        attribute.setEnabled(!attribute.getEnabled());

        attributeRepository.save(attribute);
    }

    @Override
    public void destroyAttributeById(String id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ATTRIBUTE_NOT_FOUND));

        if(attribute.isNonDeletable()) {
            throw new DataIntegrityViolationException(ATTRIBUTE_CAN_NOT_BE_DELETED);
        }

        attributeRepository.deleteById(id);
    }
}