package com.tab.buildings.service;

import com.tab.buildings.entity.Complaint;

import java.util.List;

public interface ComplaintService {
    /**
     *
     * @param complaint variable with some empty values, put by user
     * @return complaint with all values
     * @throws Exception if there is no id_manager connected with tenant in rent_agreement
     */
    Complaint addComplaint(Complaint complaint) throws Exception;

    /**
     * Function to get a list of complaints that were written by chosen tenant
     * @param tenantId to get all complaints written by chosen tenant
     * @return list of complaints written by tenant under put id
     */
    List<Complaint> getComplaints(Integer tenantId);

    /**
     * Function to get a list of complaints for manager
     * @return list of complaints
     */
    List<Complaint> getComplaintsForManager();

    /**
     * Function to get a list of complaints that were written by logged tenant
     * @return list of complaints
     */
    List<Complaint> getComplaintsForTenant();
}
