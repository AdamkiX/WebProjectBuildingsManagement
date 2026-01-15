package com.tab.buildings.service;

import com.tab.buildings.entity.Complaint;
import com.tab.buildings.rep.ComplaintRepository;
import com.tab.buildings.entity.RentAgreement;
import com.tab.buildings.rep.RentAgreementRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.JWTUtils;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    RentAgreementRepository rentAgreementRepository;
    @Autowired
    private AuthService authService;

    @Override //add complaint to table
    public Complaint addComplaint(Complaint complaint) throws Exception {
        Integer tenantId = authService.getLoggedUser();
        Integer managerId = null;
        Iterable<RentAgreement> agreements = rentAgreementRepository.findAll();

        for(RentAgreement agreement:agreements)
        {
            if(tenantId.equals(agreement.getIdTenant()))
            {
                managerId = agreement.getIdManager();
                break;
            }
        }
        if(Objects.nonNull(managerId)) {
            complaint.setIdManager(managerId);
        }
        else{
            throw new Exception("No manager found");
        }
        complaint.setIdTenant(tenantId);
        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getComplaints(Integer tenantId)
    {
        List<Complaint> complaints = new ArrayList<>();
        Iterable <Complaint> allComplaints = complaintRepository.findAll();

        for (Complaint complaint : allComplaints)
        {
            if(complaint.getIdTenant() == tenantId)
            {
                complaints.add(complaint);
            }
        }

        return complaints;
    }

    @Override
    public List<Complaint> getComplaintsForManager()
    {
        List<Complaint> complaints = new ArrayList<>();
        Integer id = authService.getLoggedUser(); //manager
        Iterable <Complaint> allComplaints = complaintRepository.findAll();
        for(Complaint complaint : allComplaints)
        {
            if(complaint.getIdManager() == id)
                complaints.add(complaint);
        }
        return complaints;
    }

    @Override
    public List<Complaint> getComplaintsForTenant()
    {
        List<Complaint> complaints = new ArrayList<>();
        Integer id = authService.getLoggedUser(); //tenant
        Iterable <Complaint> allComplaints = complaintRepository.findAll();
        for(Complaint complaint : allComplaints)
        {
            if(complaint.getIdTenant() == id)
                complaints.add(complaint);
        }
        return complaints;
    }
}
