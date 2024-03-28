package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.services.interfaces.IInvoiceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final MediaRepository mediaRepository;

    @Override
    public Media getPDFHardCopyById(String id) {
        return mediaRepository
                .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.PAYMENT, MediaType.PDF)
                .orElse(null);
    }

    @Override
    public Media getImageHardCopyById(String id) {
        return mediaRepository
                .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.PAYMENT, MediaType.IMAGE)
                .orElse(null);
    }
}