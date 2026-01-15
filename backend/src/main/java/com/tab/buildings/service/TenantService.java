package com.tab.buildings.service;

import com.tab.buildings.entity.Tenant;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.UserTenant;

import java.util.Optional;

public interface TenantService {

    /**
     * Adds new user of type tenant to database
     * @param userTenant
     * @return
     * @throws Exception
     */
    ReqRes saveUserTenant(UserTenant userTenant) throws Exception;


    ReqRes getAllTenants();

    // update operation
    Tenant updateTenant(Tenant tenant, Integer tenantId) throws Exception;

}
