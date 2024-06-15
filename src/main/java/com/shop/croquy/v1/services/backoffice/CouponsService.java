package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.coupon.CouponStoreRequest;
import com.shop.croquy.v1.dto.backoffice.coupon.CouponUpdateRequest;
import com.shop.croquy.v1.entities.Coupon;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.backoffice.interfaces.ICouponsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponsService implements ICouponsService {
    private final CouponPagingAndSortingRepository couponPagingAndSortingRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Coupon> getPaginatedCoupons(int pageNumber, int pageSize, String needle, String sort, String direction) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        if(StringUtils.isNotEmpty(needle)) {
            int discount = GeneralHelper.stringToInt(needle).orElse(-1);

            return couponPagingAndSortingRepository.findAllByCodeContainsOrDiscount(needle, discount, pageable);
        }

        return couponPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Coupon getCouponById(String id) {
        return couponRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(COUPON_NOT_FOUND));
    }

    @Override
    public void storeCouponWithCreator(CouponStoreRequest request, String creatorUsername) {
        if(couponRepository.findFistByCode(request.getCode()).isPresent()) {
            throw new DataIntegrityViolationException(COUPON_CODE_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        couponRepository.save(request.toCoupon(creator));
    }

    @Override
    public void updateCouponById(CouponUpdateRequest request, String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUPON_NOT_FOUND));

        if((coupon.getDiscount() > request.getDiscount()) || (coupon.getTotalUse() > request.getTotalUse())) {
            throw new DataIntegrityViolationException(WRONG_COUPON_UPDATE_VALUE);
        }

        Date startedAt = GeneralHelper.textToDate(request.getPromotionStartedAt()).orElse(new Date());
        Date endedAt = GeneralHelper.textToDate(request.getPromotionEndedAt()).orElse(new Date());
        if(startedAt.after(endedAt) && startedAt.getTime() != endedAt.getTime()) {
            startedAt = endedAt;
        }

        coupon.setDiscount(request.getDiscount());
        coupon.setTotalUse(request.getTotalUse());
        coupon.setPromotionStartedAt(startedAt);
        coupon.setPromotionEndedAt(endedAt);
        coupon.setDescription(request.getDescription());

        couponRepository.save(coupon);
    }

    @Override
    @Transactional
    public void destroyCouponById(String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUPON_NOT_FOUND));

        if(coupon.isNonDeletable()) {
            throw new DataIntegrityViolationException(COUPON_CAN_NOT_BE_DELETED);
        }

        couponRepository.deleteById(id);
    }

    @Override
    public void toggleCouponStatusById(String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUPON_NOT_FOUND));

        coupon.setEnabled(!coupon.getEnabled());

        couponRepository.save(coupon);
    }
}