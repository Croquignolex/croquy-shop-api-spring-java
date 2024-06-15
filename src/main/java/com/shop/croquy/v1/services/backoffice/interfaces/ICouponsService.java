package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.coupon.CouponStoreRequest;
import com.shop.croquy.v1.dto.backoffice.coupon.CouponUpdateRequest;
import com.shop.croquy.v1.entities.Coupon;

import org.springframework.data.domain.Page;

public interface ICouponsService {
    Page<Coupon> getPaginatedCoupons(int pageNumber, int pageSize, String needle, String sort, String direction);
    Coupon getCouponById(String id);
    void storeCouponWithCreator(CouponStoreRequest request, String creatorUsername);
    void updateCouponById(CouponUpdateRequest request, String id);
    void toggleCouponStatusById(String id);
    void destroyCouponById(String id);
}
