package com.tab.buildings.service;

import com.tab.buildings.entity.WorkType;
import com.tab.buildings.rep.WorkTypeRepository;
import com.tab.buildings.security.JWTUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WorkTypeServiceImpl implements WorkTypeService{
    @Autowired
    private WorkTypeRepository workTypeRepository;

    @Override
    public WorkType addWorkType(WorkType workType)
    {
        return workTypeRepository.save(workType);
    }


    @Override
    public WorkType getWorkTypeByType(String work_type)
    {
        // = workType.getWork_type();
        WorkType wt = null;
        Iterable<WorkType> workTypes = workTypeRepository.findAll();
        for(WorkType worktype : workTypes) {
            if (worktype.getWork_type().equals(work_type)) {
                wt = new WorkType();
                wt.setWork_type(worktype.getWork_type());
                wt.setId_work_type(worktype.getId_work_type());
            }
        }
        if(Objects.isNull(wt))
        {
            wt = new WorkType();
            wt.setWork_type(work_type);
            wt = addWorkType(wt);
        }
        return wt;
    }

}
