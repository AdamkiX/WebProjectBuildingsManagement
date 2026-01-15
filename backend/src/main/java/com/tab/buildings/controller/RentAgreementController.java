package com.tab.buildings.controller;

import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.RentAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Controller for managing rent agreements.
 */
@RestController
public class RentAgreementController {

    @Autowired
    RentAgreementService rentAgreementService;

    int statusCode;

    /**
     * Gets all rent agreements associated with the account.
     * @return ResponseEntity with the status and response object.
     * @throws Exception if an error occurs during the process.
     */
    @GetMapping("/agreements")
    public ResponseEntity<Object> sendAgreements() throws Exception {
        ReqRes reqRes = rentAgreementService.getAllAgreements();
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }

    /**
     * Generates rents for all agreements.
     * @return ResponseEntity with the status and response object.
     */
    @PutMapping("/agreements")
    public ResponseEntity<Object> GenerateRents() {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentAgreementService.generateRents();
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
    /**
     * Gets rents for a specific agreement.
     * @param agreement_id The ID of the agreement.
     * @return ResponseEntity with the status and response object.
     */
    @GetMapping("/agreements/{agreement_id}")
    public ResponseEntity<Object> GenerateRents(@PathVariable("agreement_id") Integer agreement_id) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentAgreementService.getRents(agreement_id);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }



}
