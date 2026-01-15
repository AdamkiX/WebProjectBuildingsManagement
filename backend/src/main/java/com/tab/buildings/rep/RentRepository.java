package com.tab.buildings.rep;

import com.tab.buildings.entity.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository  extends CrudRepository<Rent, Integer> {

}
