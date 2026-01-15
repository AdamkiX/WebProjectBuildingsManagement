package com.tab.buildings.controller;

import com.tab.buildings.entity.Tenant;
import com.tab.buildings.rep.TenantRepository;
import com.tab.buildings.service.TenantService;
import com.tab.buildings.wrapper.UserTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for managing tenants.
 */
@RestController
public class TenantController {

    private int statusCode;
    // Annotation
    @Autowired
    private TenantService tenantService;
    @Autowired
    private TenantRepository tenantRepository;


    /**
     * Manager's ability to add a new tenant account to the database.
     * @param userTenant The tenant information to be saved.
     * @return ResponseEntity with the status and response object.
     * @throws Exception if an error occurs during the process.
     */
    @PostMapping("/managers/tenants")
    public ResponseEntity<Object> saveTenant(
            @Valid @RequestBody UserTenant userTenant) throws Exception {
        statusCode = ResponseEntity.ok(tenantService.saveUserTenant(userTenant)).getBody().getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(tenantService.saveUserTenant(userTenant)));
    }
    /**
     * Updates an existing tenant's information.
     * @param tenant The updated tenant information.
     * @param tenantId The ID of the tenant to be updated.
     * @return ResponseEntity with the status and response object.
     * @throws Exception if an error occurs during the process.
     */
    // Update operation
    @PutMapping("/tenants/{id}")
    public ResponseEntity<Object> updateTenant(@RequestBody Tenant tenant, @PathVariable("id") Integer tenantId) throws Exception {

        return ResponseEntity.ok(tenantService.updateTenant(tenant, tenantId));
    }

}
