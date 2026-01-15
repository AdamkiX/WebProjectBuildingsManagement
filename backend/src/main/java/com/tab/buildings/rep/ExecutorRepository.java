package com.tab.buildings.rep;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tab.buildings.entity.Executor;
@Repository
public interface ExecutorRepository extends CrudRepository<Executor, Integer>{

}