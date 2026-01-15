package com.tab.buildings.wrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tab.buildings.entity.Executor;
import com.tab.buildings.rep.ExecutorRepository;
import com.tab.buildings.service.ExecutorService;
import com.tab.buildings.entity.WorkType;
import com.tab.buildings.rep.WorkTypeRepository;
import com.tab.buildings.service.WorkTypeService;
import com.tab.buildings.security.JWTUtils;

import java.util.ArrayList;
import java.util.List;


public class Executor_WorkType {
    private WorkType workType;
    private Executor executor;

    /**
     * Constructor of class
     * @param executor executor from table
     * @param workType eorkType from table
     */
    public Executor_WorkType(Executor executor, WorkType workType)
    {
        this.executor = executor;
        this.workType = workType;
    }

    /**
     * Getter of executor variable
     * @return executor
     */
    public Executor getExecutor() {return executor;}

    /**
     * Getter of workType variable
     * @return workType
     */
    public  WorkType getWorkType() {return workType;}

    /**
     * Setter of executor variable
     * @param executor to set executor
     */
    public void setExecutor(Executor executor) {this.executor = executor;}

    /**
     * Seter of workType variable
     * @param workType to ser workType
     */
    public void setWorkType(WorkType workType) {this.workType = workType;}

}
