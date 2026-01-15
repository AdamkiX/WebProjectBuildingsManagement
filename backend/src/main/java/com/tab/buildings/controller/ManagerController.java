package com.tab.buildings.controller;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.RentAgreement;
import com.tab.buildings.rep.ManagerRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.*;
import com.tab.buildings.wrapper.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class  ManagerController {

    private int statusCode;
    // Annotation
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RentAgreementService rentAgreementService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private TenantService tenantService;

    /**
     * Adding Rent Agreement by using manager account
     * @param rentAgreement
     * @return
     * @throws Exception
     */
    @PostMapping("/managers/agreement")
    public ResponseEntity<Object> addAgreement(@Valid @RequestBody RentAgreement rentAgreement) throws Exception{
        Apartment apartment = new Apartment();
        apartment.setIdTenant(rentAgreement.getIdTenant());
        ReqRes reqRes = rentAgreementService.saveAgreement(rentAgreement);
        statusCode = reqRes.getStatusCode();

        if(statusCode != 200) {
            return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
        }

        ReqRes apartmentStuff = apartmentService.addTenant(rentAgreement.getIdApartment(),rentAgreement.getIdTenant());
        statusCode = apartmentStuff.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }

    /**
     * Editing Rent Agreement by using manager account
     * @param rentAgreement
     * @return
     * @throws Exception
     */
    @PutMapping("/managers/agreement")
    public ResponseEntity<Object> editAgreement(@Valid @RequestBody RentAgreement rentAgreement) throws Exception{
        ReqRes reqRes = rentAgreementService.editAgreement(rentAgreement);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }

    /**
     * Adding new manager to database
     * @param userManager
     * @return
     * @throws Exception
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<Object> saveManager(
            @Valid @RequestBody UserManager userManager) throws Exception {
        ReqRes reqRes = managerService.saveUserManager(userManager);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

    // Update operation
    @PutMapping("/managers/{id}")
    public ResponseEntity<Object> updateManager(@RequestBody Manager manager, @PathVariable("id") Integer managerId) throws Exception {

        return ResponseEntity.ok(managerService.updateManager(manager, managerId));
    }

    /**
     * Deleting tenant from apartment
     * @param idApartment
     * @return
     * @throws Exception
     */
    @DeleteMapping("/managers/tenant_in_apartment")
    public ResponseEntity<Object> deleteTenantFromApartment(@RequestBody Integer idApartment) throws Exception{
        ReqRes reqRes = apartmentService.deleteTenantFromApartment(idApartment);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

    @GetMapping("/managers/tenants")
    public ResponseEntity<Object> getAllTenants() {
        ReqRes reqRes = tenantService.getAllTenants();
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

}
