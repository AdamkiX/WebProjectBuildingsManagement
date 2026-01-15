package com.tab.buildings.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tab.buildings.entity.Building;
import com.tab.buildings.rep.BuildingRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BuildingController {
    // Annotation
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Handles the HTTP POST request to save a building.
     *
     * @param building The Building object to be saved. It must be valid as per the specified constraints.
     * @return A ResponseEntity containing the response from the buildingService and an appropriate HTTP status code.
     */
    @PostMapping("/managers/buildings")
    public ResponseEntity<Object> saveBuilding(@Valid @RequestBody Building building)
    {

        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = buildingService.saveBuilding(building);
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
     * Handles the HTTP GET request to fetch a building by its ID.
     *
     * @param id_building The ID of the building to be fetched.
     * @return A ResponseEntity containing the response from the buildingService and an appropriate HTTP status code.
     */
    @GetMapping("/managers/buildings/{id}")
    public ResponseEntity<Object> fetchBuilding(@PathVariable("id") Integer id_building) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = buildingService.findById(id_building);
        int statusCode = reqRes.getStatusCode();
        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Handles the HTTP GET request to fetch all buildings.
     *
     * @return A ResponseEntity containing the response from the buildingService and an appropriate HTTP status code.
     * @throws Exception if an error occurs during fetching buildings.
     */
    @GetMapping("/managers/buildings")
    public ResponseEntity<Object> fetchBuildings() throws Exception  {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = buildingService.fetchBuildings();
        int statusCode = reqRes.getStatusCode();
        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Handles the HTTP PUT request to update a building.
     *
     * @param json The JSON object containing the building data to be updated.
     * @return A ResponseEntity containing the response from the buildingService and an appropriate HTTP status code.
     * @throws Exception if an error occurs during updating the building.
     */
    @PutMapping("/buildings")
    public ResponseEntity<Object> updateBuilding(@RequestBody ObjectNode json ) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = buildingService.updateBuilding(json);
        int statusCode = reqRes.getStatusCode();
        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 201:
                return ResponseEntity.status(HttpStatus.CREATED).body(reqRes);
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
    /**
     * Handles the HTTP DELETE request to delete a building by its ID.
     *
     * @param id_building The ID of the building to be deleted.
     * @return A ResponseEntity containing the response from the buildingService and an appropriate HTTP status code.
     */
    @DeleteMapping("/buildings/{id}")
    public ResponseEntity<Object> deleteBuildingById(@PathVariable("id") Integer id_building)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = buildingService.deleteBuildingById(id_building);
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






}

