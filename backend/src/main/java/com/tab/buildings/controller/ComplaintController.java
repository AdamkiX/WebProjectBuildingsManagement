package com.tab.buildings.controller;

import com.tab.buildings.entity.Complaint;
import com.tab.buildings.rep.ComplaintRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
public class ComplaintController {
    //Annotation
    @Autowired
    ComplaintRepository complaintRepository;
    @Autowired
    ComplaintService complaintService;
    @Autowired
    private AuthService authService;

    /**
     * Function to create complaints,
     * @param complaint title, date and content of complaint
     *                  id_complaint, id_manager and id_tenant are set in function
     * @return added compaint
     * @throws Exception when in table rent_agreement is no one manager attached to logged tenant
     */
    @PostMapping("/tenant/complaint")
    public ResponseEntity<Object> addComplaint(@Valid @RequestBody Complaint complaint) throws Exception
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as tenant");
        }
        if(Objects.isNull(complaint.getDate()) || Objects.isNull(complaint.getTitle()) || Objects.isNull(complaint.getContent()))
            return ResponseEntity.status(400).body("Bad input");
        Complaint complainttemp = null;
        try{
            complainttemp = complaintService.addComplaint(complaint);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(403).body("Tenant has no rent agreement");
        }

        return ResponseEntity.status(200).body(ResponseEntity.ok(complainttemp));
    }

    /**
     * Function to get all complaints made by logged tenant
     * @return list of all logged tenant's complaints
     */
    @GetMapping("/tenant/complaint")
    public ResponseEntity<Object> ReadComplaintsHistory()
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as tenant");
        }
        return ResponseEntity.status(200).body(ResponseEntity.ok(complaintService.getComplaintsForTenant()));
    }

    /**
     * Function to get all complaints which are send to logged manager
     * @return list of all complaint for logged manager
     */
    @GetMapping("/manager/complaint")
    public ResponseEntity<Object> ReadComplaints()
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }
        return ResponseEntity.status(200).body(ResponseEntity.ok(complaintService.getComplaintsForManager()));
    }

    /**
     * To get all complaints from certain tenant as manager
     * @param TenantId to get all complaints which were made by tenant under TenantId
     * @return list of all complaints from tenant under put TenantId
     */
    @PostMapping("/manager/complaint/{id}")
    public ResponseEntity<Object> ReadComplaintsFromTenant(@PathVariable("id") Integer TenantId)
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }
        return ResponseEntity.status(200).body(ResponseEntity.ok(complaintService.getComplaints(TenantId)));
    }
}