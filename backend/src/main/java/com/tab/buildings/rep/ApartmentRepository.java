package com.tab.buildings.rep;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {
    List<Apartment> findByIdManager(Manager idManager);
}
