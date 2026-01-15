package com.tab.buildings.rep;

import com.tab.buildings.entity.Repair;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends CrudRepository<Repair, Integer> {
}
