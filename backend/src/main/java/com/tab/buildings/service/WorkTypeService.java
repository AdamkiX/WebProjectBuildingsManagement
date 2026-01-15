package com.tab.buildings.service;
import com.tab.buildings.entity.WorkType;
import com.tab.buildings.rep.WorkTypeRepository;

import java.util.List;

public interface WorkTypeService {
    /**
     *
     * @param workType is new worktype to add
     * @return variable to check if is added properly
     */
    WorkType addWorkType(WorkType workType);

    /**
     * Function to get whole variable by put name of worktype,
     * if put name doesn't exist, it creates new one and add it to table
     * @param work_type string that contains name of worktype
     * @return whole worktype parameter with id
     */
    WorkType getWorkTypeByType(String work_type);
}
