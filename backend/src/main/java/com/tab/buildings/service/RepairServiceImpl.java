package com.tab.buildings.service;

import com.tab.buildings.entity.Repair;
import com.tab.buildings.entity.RepairCom;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.rep.RepairComRepository;
import com.tab.buildings.rep.RepairRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.RepairAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairRepository repairRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RepairComRepository repairComRepository;
    @Autowired
    private AuthService authService;

    // Adding new Repair
    @Override
    public ReqRes saveRepair(RepairAdd repair) {
        ReqRes reqRes = new ReqRes();

        Integer id = authService.getLoggedUser();
        RepairCom repairCom = repairComRepository.findById(repair.getIdRepairCom()).get();
        try {
            repair.getIdRepairCom().compareTo(0);
        } catch (NullPointerException e) {
            reqRes.setMessage("No matching commissions or no selected commission");
            reqRes.setStatusCode(404);
            return reqRes;
        }
        int idCommission;
        try {
            idCommission = repairCom.getId();
        } catch (NullPointerException e) {
            reqRes.setStatusCode(404);
            reqRes.setMessage("No repair commission found");
            return reqRes;
        }
        repair.getRepair().setIdManager(id);
        repair.getRepair().setIdRepairCom(idCommission);
        repair.getRepair().setStatus("pending");
        try {
            repairRepository.save(repair.getRepair());
        } catch (Exception e) {
            reqRes.setStatusCode(400);
            reqRes.setMessage("You've entered something wrong. Please check if your repair has properly set values.");
            return reqRes;
        }
        reqRes.setStatusCode(200);
        reqRes.setMessage("Repair successfully added");
        return reqRes;
    }

    // Getting list of Repairs
    @Override
    public ReqRes getRepairs() {
        ReqRes reqRes = new ReqRes();
        Integer id = authService.getLoggedUser();
        Iterable<RepairCom> repairComs = repairComRepository.findAll();
        Iterable<Repair> repairs = repairRepository.findAll();
        List<Repair> myRepairs = new ArrayList<>();
        List<RepairCom> repairComsList = new ArrayList<>();

        try {
            // Seeking for manager apartment and saving it
            for (RepairCom repairCom : repairComs) {
                if (apartmentRepository.findById(repairCom.getIdApartment()).get().getIdManager() == id) {
                    for (Repair repair : repairs) {
                        if (Objects.equals(repair.getIdRepairCom(), repairCom.getId())) {
                            myRepairs.add(repair);
                        }
                    }
                }
            }

            reqRes.setRepairs(myRepairs);
            reqRes.setMessage("Fetch was successfull!");
            if (myRepairs.isEmpty()) {
                reqRes.setMessage(reqRes.getMessage() + " None of your apartments have repairs");
            }
            reqRes.setStatusCode(200);
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
            return reqRes;
        }
    }

    // Editing specific repair in specific apartment
    @Override
    public ReqRes editRepair(Repair repair, int idRepair) {
        ReqRes reqRes = new ReqRes();
        Integer id = authService.getLoggedUser();
        Repair depDB;

        try {
            depDB = repairRepository.findById(idRepair).orElse(null);
        } catch (NullPointerException e) {
            reqRes.setStatusCode(404);
            reqRes.setMessage("No repair found");
            return reqRes;
        }

        if (Objects.nonNull(repair.getCost())) {
            try {
                depDB.setCost(repair.getCost());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set cost of repair. Wrong or to large cost parsed: " + repair.getCost());
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getStartDate()) && !"".equalsIgnoreCase(repair.getStartDate())) {
            try {
                depDB.setStartDate(repair.getStartDate());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set start date. Wrong date parsed: " + repair.getStartDate());
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getEndDate()) && !"".equalsIgnoreCase(repair.getEndDate())) {
            try {
                depDB.setEndDate(repair.getEndDate());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set end date. Wrong date parsed: " + repair.getPlannedStartDate());
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getPlannedEndDate()) && !"".equalsIgnoreCase(repair.getPlannedEndDate())) {
            try{
                depDB.setPlannedEndDate(repair.getPlannedEndDate());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set planned end date. Wrong date parsed: " + repair.getPlannedEndDate());
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getPlannedStartDate()) && !"".equalsIgnoreCase(repair.getPlannedStartDate())) {
            try {
                depDB.setPlannedStartDate(repair.getPlannedStartDate());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set planned start date. Wrong date parsed: " + repair.getPlannedStartDate());
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getIdExecutor())) {
            try {
                depDB.setIdExecutor(repair.getIdExecutor());
                repairRepository.save(depDB);
            } catch (NullPointerException e) {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No repair executor found");
                return reqRes;
            }
        }
        if (Objects.nonNull(repair.getStatus()) && !"".equalsIgnoreCase(repair.getStatus())) {
            try {
                depDB.setStatus(repair.getStatus());
                repairRepository.save(depDB);
            } catch (Exception e) {
                reqRes.setStatusCode(400);
                reqRes.setMessage("Couldn't set Status. Wrong status parsed: " + repair.getStatus());
                return reqRes;
            }
        }
            // Here to handle executor_deletion form row
        try {
            repairRepository.save(depDB);
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
            return reqRes;
        }

        reqRes.setStatusCode(200);
        reqRes.setMessage("Repair successfully updated");
        return reqRes;
    }

}
