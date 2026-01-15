package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Repair;

public class RepairAdd {
    private Repair repair;
    private Integer idRepairCom;
    public RepairAdd(Repair repair, Integer idRepairCom) {
        this.repair = repair;
        this.idRepairCom = idRepairCom;
    }
    public Repair getRepair() { return repair; }
    public Integer getIdRepairCom() { return idRepairCom;}

    public void setRepair(Repair repair) { this.repair = repair;}
    public void setIdRepairCom(Integer idRepairCom) { this.idRepairCom = idRepairCom;}
}
