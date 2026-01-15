package com.tab.buildings.controller;

import com.tab.buildings.entity.Rent;
import com.tab.buildings.rep.RentRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RentController {
    // Annotation
    @Autowired
    private RentService rentService;
    @Autowired
    private RentRepository rentRepository;
    public int statusCode;


    /**
     * Fetches the rent by its ID from the database.
     * Verifies if the rent exists and if the current user is authorized to access it.
     *
     * @param id_rent The ID of the rent to be fetched.
     * @return A {@link ResponseEntity} object containing the rent details and status code.
     */
    @GetMapping("/rents/{id}")
    public ResponseEntity<Object> fetchRent(@PathVariable("id") Integer id_rent) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentService.fetchRentById(id_rent);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
    /**
     * Fetches all rents associated with the logged-in user from the database.
     * Ensures only the rents related to the user's managed or tenanted apartments are returned.
     *
     * @return A {@link ResponseEntity} object containing the list of rents and status code.
     * @throws Exception If an error occurs while fetching the rents.
     */
    @GetMapping("/rents/all")
    public ResponseEntity<Object> fetchAllRents() throws Exception  {

        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentService.fetchAllRents();
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Adds a new rent to the database.
     * Validates the rent details and ensures the user is authorized to add the rent to the specified apartment.
     *
     * @param rent The rent object containing new rent details.
     * @return A {@link ResponseEntity} object containing the status code.
     */
    @PutMapping("/managers/rents")
    public ResponseEntity<Object> addNewRent(@RequestBody Rent rent) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentService.addNewRent(rent);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Updates an existing rent in the database if it exists.
     * Validates the rent details and ensures the user is authorized to update the rent.
     *
     * @param rent The rent object containing updated details.
     * @return A {@link ResponseEntity} object containing the status code.
     */
    @PutMapping("/managers/rents/update")
    public ResponseEntity<Object> updateRentById(@RequestBody Rent rent){
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentService.updateIfExists(rent);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Deletes the rent by its ID from the database.
     * Ensures the rent exists and the current user is authorized to delete it.
     *
     * @param id_rent The ID of the rent to be deleted.
     * @return A {@link ResponseEntity} object containing the status code and message.
     */
    @DeleteMapping("/rent/delete/{id}")
    public ResponseEntity<Object> deleteRentById(@PathVariable("id") Integer id_rent)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = rentService.deleteRentById(id_rent);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }






}

