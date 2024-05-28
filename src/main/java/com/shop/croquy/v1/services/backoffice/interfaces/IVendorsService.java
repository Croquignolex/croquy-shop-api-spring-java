package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.vendor.VendorStoreRequest;
import com.shop.croquy.v1.dto.backoffice.vendor.VendorUpdateRequest;
import com.shop.croquy.v1.dto.web.AddressUpdateRequest;
import com.shop.croquy.v1.entities.Vendor;
import com.shop.croquy.v1.entities.address.VendorAddress;
import com.shop.croquy.v1.entities.media.VendorLogo;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IVendorsService {
    Page<Vendor> getPaginatedVendors(int pageNumber, int pageSize, String needle);
    Vendor getVendorById(String id);
    void storeVendorWithCreator(VendorStoreRequest request, String creatorUsername);
    void updateVendorById(VendorUpdateRequest request, String id);
    void toggleVendorStatusById(String id);
    void destroyVendorById(String id);
    void destroyVendorAddressById(String id);
    VendorAddress updateVendorAddressById(AddressUpdateRequest request, String id, String creatorUsername);
    VendorLogo changeVendorLogoById(MultipartFile image, String id, String creatorUsername);
    void destroyVendorLogoById(String id);
}
