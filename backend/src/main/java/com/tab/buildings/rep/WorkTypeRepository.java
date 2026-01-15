package com.tab.buildings.rep;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tab.buildings.entity.WorkType;

@Repository
public interface WorkTypeRepository extends CrudRepository<WorkType,Integer>{
}