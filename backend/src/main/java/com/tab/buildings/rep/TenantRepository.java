package com.tab.buildings.rep;

import com.tab.buildings.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TenantRepository extends CrudRepository<Tenant, Integer> {

}
