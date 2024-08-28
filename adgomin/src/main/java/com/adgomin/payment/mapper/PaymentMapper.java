package com.adgomin.payment.mapper;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {
    void setPhoneNumberCertification(JoinEntity joinEntity);

    JoinVO phoneNumberCertificationCheck(@Param(value = "userOrder") int userOrder, @Param(value = "phoneNumberCertification") String phoneNumberCertification);
    JoinVO getVerificationInfo(@Param(value = "userOrder") int userOrder);

    int phoneNumberCertificationSuccess(JoinEntity joinEntity);
}
