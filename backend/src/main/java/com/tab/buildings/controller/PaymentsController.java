package com.tab.buildings.controller;

import com.tab.buildings.entity.Payment;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.PaymentsService;
import com.tab.buildings.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {

    int statusCode;

    @Autowired
    private RoomService roomService;
    @Autowired
    private PaymentsService paymentsService;

    /**
     * Endpoint for adding a payment.
     * @param payment The payment to be added.
     * @return ResponseEntity with the status and response object.
     */
    @PostMapping("/tenants/pay")
    public ResponseEntity<Object> addPayment(@RequestBody Payment payment) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = paymentsService.pay(payment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
    /**
     * Endpoint for deleting a payment.
     * @param payment The payment to be deleted.
     * @return ResponseEntity with the status and response object.
     */
    @PostMapping("/tenants/delete_payment")
    public ResponseEntity<Object> deletePayment(@RequestBody Payment payment) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = paymentsService.deletePayment(payment.getId_payment());
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Endpoint for getting all payments for tenants.
     * @return ResponseEntity with the status and response object.
     */
    @GetMapping("/tenants/all_payments")
    public ResponseEntity<Object> getPayments() {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = paymentsService.getPayments();
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Endpoint for getting all payments for managers.
     * @return ResponseEntity with the status and response object.
     */
    @GetMapping("/managers/all_payments")
    public ResponseEntity<Object> getManagerPayments() {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = paymentsService.getManagerPayments();
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }


}
