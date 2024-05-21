package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.vendor.VendorStoreRequest;
import com.shop.croquy.v1.dto.backoffice.vendor.VendorUpdateRequest;
import com.shop.croquy.v1.dto.web.AddressUpdateRequest;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.entities.Vendor;
import com.shop.croquy.v1.entities.address.VendorAddress;
import com.shop.croquy.v1.entities.media.VendorLogo;
import com.shop.croquy.v1.helpers.ImageOptimisationHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.interfaces.IVendorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class VendorsService implements IVendorService {
    private final VendorPagingAndSortingRepository vendorPagingAndSortingRepository;
    private final VendorAddressRepository vendorAddressRepository;
    private final VendorLogoRepository vendorLogoRepository;
    private final VendorRepository vendorRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @Override
    public Page<Vendor> getPaginatedVendors(int pageNumber, int pageSize, String needle) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return vendorPagingAndSortingRepository.findAllByNameContainsOrCreatorIsIn(needle, users, pageable);
        }

        return vendorPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Vendor getVendorById(String id) {
        return vendorRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));
    }

    @Override
    public void storeVendorCreator(VendorStoreRequest request, String creatorUsername) {
        if(vendorRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(VENDOR_NAME_ALREADY_EXIST + request.getName());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        vendorRepository.save(request.toVendor(creator));
    }

    @Override
    public void updateVendorById(VendorUpdateRequest request, String id) {
        if(vendorRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(VENDOR_NAME_ALREADY_EXIST + request.getName());
        }

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        vendor.setName(request.getName());
        vendor.setDescription(request.getDescription());

        vendorRepository.save(vendor);
    }

    @Override
    public void toggleVendorStatusById(String id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        vendor.setEnabled(!vendor.getEnabled());

        vendorRepository.save(vendor);
    }

    @Override
    public void destroyVendorById(String id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        if(vendor.isNonDeletable()) {
            throw new DataIntegrityViolationException(VENDOR_CAN_NOT_BE_DELETED);
        }

        if(vendor.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(vendor.getLogo().getPath(), mediaFolderPath);
            vendorLogoRepository.delete(vendor.getLogo());
        }

        if(vendor.getAddress() != null) {
            vendorAddressRepository.delete(vendor.getAddress());
        }

        vendorRepository.deleteById(id);
    }

    @Override
    public VendorLogo changeVendorLogoById(MultipartFile image, String id, String creatorUsername) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        VendorLogo vendorLogo = vendor.getLogo();

        if(vendorLogo != null) ImageOptimisationHelper.deleteFile(vendor.getLogo().getPath(), mediaFolderPath);
        else vendorLogo = new VendorLogo();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        vendorLogo.setSize(image.getSize());
        vendorLogo.setOriginalName(savedFileDic.get("name"));
        vendorLogo.setPath(savedFileDic.get("path"));
        vendorLogo.setVendor(vendor);
        vendorLogo.setCreator(creator);

        vendorLogoRepository.save(vendorLogo);

        return vendorLogo;
    }

    @Override
    public void destroyVendorLogoById(String id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        if(vendor.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(vendor.getLogo().getPath(), mediaFolderPath);
            vendorLogoRepository.delete(vendor.getLogo());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }

    @Override
    public VendorAddress updateVendorAddressById(AddressUpdateRequest request, String id, String creatorUsername) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        VendorAddress vendorAddress = vendor.getAddress();

        if(vendorAddress == null) vendorAddress = new VendorAddress();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);
        var state = stateRepository.findById(request.getStateId()).orElse(null);

        vendorAddress.setStreetAddress(request.getStreetAddress());
        vendorAddress.setPhoneNumberOne(request.getPhoneNumberOne());
        vendorAddress.setPhoneNumberTwo(request.getPhoneNumberTwo());
        vendorAddress.setZipcode(request.getZipcode());
        vendorAddress.setDescription(request.getDescription());
        vendorAddress.setVendor(vendor);
        vendorAddress.setState(state);
        vendorAddress.setCreator(creator);

        vendorAddressRepository.save(vendorAddress);

        return vendorAddress;
    }

    @Override
    public void destroyVendorAddressById(String id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(VENDOR_NOT_FOUND));

        if(vendor.getAddress() != null) {
            vendorAddressRepository.delete(vendor.getAddress());
        } else {
            throw new DataIntegrityViolationException(ADDRESS_NOT_FOUND);
        }
    }
}