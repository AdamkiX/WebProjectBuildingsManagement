package com.tab.buildings.rep;

import com.tab.buildings.entity.RentAgreement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentAgreementRepository extends CrudRepository<RentAgreement, Integer> {
}
