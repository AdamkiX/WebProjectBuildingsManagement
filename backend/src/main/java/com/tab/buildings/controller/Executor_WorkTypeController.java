package com.tab.buildings.controller;
import com.tab.buildings.entity.Executor;
import com.tab.buildings.entity.WorkType;
import com.tab.buildings.rep.ExecutorRepository;
import com.tab.buildings.rep.WorkTypeRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.service.ExecutorService;
import com.tab.buildings.service.WorkTypeService;
import com.tab.buildings.wrapper.Executor_WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class Executor_WorkTypeController {

    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    WorkTypeService workTypeService;
    @Autowired
    ExecutorRepository executorRepository;
    @Autowired
    ExecutorService executorService;
    @Autowired
    private AuthService authService;

    /**
     * Function to put new executor in table "executor"
     * and new worktype, if put worktype doesn't exist, create new one
     * if exists, do nothing with it
     *
     * @param executorWorkType parameter that containst variables for worktype and executor
     *                         it contains worktype name,
     *                         executor's name, surname, phone, birthdate, address, company_name, nip, regon,
     *                         in function is created and put id_executor and id_work_type
     * @return whole variable with missing values
     */
    @PostMapping("/manager/executor")
    public ResponseEntity<Object> addExecutor(@Valid @RequestBody Executor_WorkType executorWorkType)
    {
        //statusCode
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }

        WorkType wt = executorWorkType.getWorkType();

        if(Objects.isNull(wt.getWork_type()))
        {
            return ResponseEntity.status(400).body("Put executor's work type");
        }
        wt = workTypeService.getWorkTypeByType(wt.getWork_type());

        Executor excecutor = executorWorkType.getExecutor();
        //.matches("^\\d+$")
        if(!excecutor.getPhone().matches("^\\d+$"))
        {
            return ResponseEntity.status(400).body("Phone has to have only numbers");
        }
        if(!excecutor.getNip().matches("^\\d+$"))
        {
            return ResponseEntity.status(400).body("Nip has to have only numbers");
        }
        if(!excecutor.getRegon().matches("^\\d+$"))
        {
            return ResponseEntity.status(400).body("Regon has to have only numbers");
        }

        excecutor.setId_work_type(wt.getId_work_type());

        Executor ewt = executorService.addExecutor(excecutor);

        return ResponseEntity.status(200).body(ResponseEntity.ok(ewt));
    }

    /**
     * function to get every executor from table and their attached worktype
     * @return List of all executors and their worktype
     */
    @GetMapping("/manager/executor")
    public ResponseEntity<Object> getExecutors()
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }

        List<Executor_WorkType> executors_workTypes = new ArrayList<>();
        Iterable<Executor> executors = executorRepository.findAll();
        Iterable<WorkType> workTypes = workTypeRepository.findAll();
        for(Executor executor : executors)
        {
            Executor ex = new Executor();
            WorkType wt = new WorkType();

            ex.setId_executor(executor.getId_executor());
            ex.setName(executor.getName());
            ex.setSurname(executor.getSurname());
            ex.setPhone(executor.getPhone());
            ex.setBirthdate(executor.getBirthdate());
            ex.setCompany_name(executor.getCompany_name());
            ex.setAddress(executor.getAddress());
            ex.setNip(executor.getNip());
            ex.setRegon(executor.getRegon());
            ex.setId_work_type(executor.getId_work_type());

            for(WorkType workType : workTypes)
            {
                if(workType.getId_work_type() == executor.getId_work_type())
                {
                    wt.setId_work_type(workType.getId_work_type());
                    wt.setWork_type(workType.getWork_type());
                }
            }
            Executor_WorkType ewt = new Executor_WorkType(ex,wt);
            executors_workTypes.add(ewt);
        }
        return ResponseEntity.status(200).body(ResponseEntity.ok(executors_workTypes)) ;
    }

}
