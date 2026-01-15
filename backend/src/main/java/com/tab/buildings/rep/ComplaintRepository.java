package com.tab.buildings.rep;

import com.tab.buildings.entity.Complaint;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends CrudRepository<Complaint, Integer>{

}
