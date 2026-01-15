package com.tab.buildings.rep;

import com.tab.buildings.entity.RepairCom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairComRepository extends CrudRepository<RepairCom, Integer> {
}
