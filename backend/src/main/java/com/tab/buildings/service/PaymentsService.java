package com.tab.buildings.service;

import com.tab.buildings.entity.Payment;
import com.tab.buildings.entity.Rent;
import com.tab.buildings.security.ReqRes;

public interface PaymentsService {

    public ReqRes pay(Payment payment);

    ReqRes getPayments();
    ReqRes getManagerPayments();

    ReqRes deletePayment(Integer id_payment);
}
