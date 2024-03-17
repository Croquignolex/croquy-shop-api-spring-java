package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.models.Media;
import com.shop.croquy.v1.repositories.MediaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final MediaRepository mediaRepository;

    @Override
    public Media getHardCopy(String id) {
         return mediaRepository.findByMediaMorphIdAndMediaMorphType(id, MediaMorphType.INVOICE).orElse(null);
    }

}