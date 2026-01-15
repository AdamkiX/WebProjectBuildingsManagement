package com.tab.buildings.service;

import com.tab.buildings.entity.Tenant;
import com.tab.buildings.entity.User;
import com.tab.buildings.rep.TenantRepository;
import com.tab.buildings.rep.UserRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.UserTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TenantServiceImpl implements TenantService{

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;

    // save operation
    @Override
    public ReqRes saveUserTenant(UserTenant request) throws Exception {
        ReqRes reqRes = new ReqRes();
        try {
            String password;
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user = request.getUser();
            Tenant tenant = request.getTenant();

            tenantRepository.save(tenant);
            user.setUsertype("tenant");
            user.setTenantUserId(tenant.getId());
            password = randomPassword();
            user.setPassword(password);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            reqRes.setStatusCode(200);
            reqRes.setMessage("You've successfully saved the tenant " + tenant.getName());
            reqRes.setUsername(user.getUsername());
            reqRes.setPassword(password);

            return reqRes;
        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
            tenantRepository.delete(request.getTenant());
            return reqRes;
        }
    }

    // method to generate random string
    public String randomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    // update operation
    @Override
    public Tenant updateTenant(Tenant tenant, Integer tenantId) throws Exception {
        Tenant depDB = tenantRepository.findById(tenantId).get();

        if (Objects.nonNull(tenant.getName()) && !"".equalsIgnoreCase(tenant.getName())) {
            depDB.setName(tenant.getName());
        }

        if (Objects.nonNull(tenant.getSurname()) && !"".equalsIgnoreCase(tenant.getSurname())) {
            depDB.setSurname(tenant.getSurname());
        }

        if (Objects.nonNull(tenant.getBirthdate()) && !"".equalsIgnoreCase(tenant.getBirthdate())) {
            depDB.setBirthdate(tenant.getBirthdate());
        }

        if (Objects.nonNull(tenant.getPhone())) {
            depDB.setPhone(tenant.getPhone());
        }

        if (Objects.nonNull(tenant.getResidence()) && !"".equalsIgnoreCase(tenant.getResidence())) {
            depDB.setResidence(tenant.getResidence());
        }

        return tenantRepository.save(depDB);
    }

    @Override
    public ReqRes getAllTenants() {
        ReqRes reqRes = new ReqRes();
        try {
            Iterable<Tenant> tenants = tenantRepository.findAll();
            List<Tenant> allTenants = new ArrayList<>();

            for(Tenant tenant : tenants) {
                allTenants.add(tenant);
            }
            reqRes.setTenants(allTenants);
            reqRes.setMessage("Successfully fetched tenants");
            reqRes.setStatusCode(200);
        } catch (Exception e) {
            reqRes.setMessage("Internal server error");
            reqRes.setStatusCode(500);
            return reqRes;
        }
        return reqRes;
    }

}
