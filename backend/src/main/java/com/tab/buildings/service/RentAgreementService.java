package com.tab.buildings.service;

import com.tab.buildings.entity.RentAgreement;
import com.tab.buildings.entity.User;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.UserManager;

import java.util.List;

public interface RentAgreementService {

    /**
     * Adds new rent agreement to database
     * @param rentAgreement
     * @return
     */
    ReqRes saveAgreement(RentAgreement rentAgreement);

    /**
     * Returns all rent agreements associated to account
     * @return
     * @throws Exception
     */
    ReqRes getAllAgreements() throws Exception;

    /**
     * Updates specific rent agreement in database
     * @param rentAgreement
     * @return
     */
    ReqRes editAgreement(RentAgreement rentAgreement);

    ReqRes generateRents();

    ReqRes getRents(Integer agreement_id);
}
