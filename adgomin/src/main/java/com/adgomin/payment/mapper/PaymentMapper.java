package com.adgomin.payment.mapper;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.payment.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {
    void setPhoneNumberCertification(JoinEntity joinEntity);
    JoinVO phoneNumberCertificationCheck(@Param(value = "userOrder") int userOrder, @Param(value = "phoneNumberCertification") String phoneNumberCertification);
    JoinVO getVerificationInfo(@Param(value = "userOrder") int userOrder);
    int phoneNumberCertificationSuccess(JoinEntity joinEntity);
    int paymentRegister(PaymentsEntity paymentsEntity);
    JoinVO getSellerOrder(@Param(value = "mediaOrder") int mediaOrder);
    void insertPaymentFailed(PaymentFailedEntity paymentFailedEntity);
    void insertPayments(PaymentsEntity paymentsEntity);
    void insertPaymentsCard(PaymentsCardEntity paymentsCardEntity);
    void insertPaymentsBank(PaymentsBankEntity paymentsBankEntity);
    void insertPaymentsVbank(PaymentsVbankEntity paymentsVbankEntity);
}
