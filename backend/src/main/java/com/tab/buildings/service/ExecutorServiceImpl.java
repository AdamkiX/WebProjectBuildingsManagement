package com.tab.buildings.service;
import com.tab.buildings.entity.Executor;
import com.tab.buildings.rep.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutorServiceImpl implements ExecutorService{

    @Autowired
    private ExecutorRepository executorRepository;

    @Override
    public Executor addExecutor (Executor executor)
    {
        return executorRepository.save(executor);
    }
    @Override
    public List<Executor> getExecutorsByWorkType(Integer work_type_id)
    {
        List<Executor> executors = new ArrayList<>();
        return executors;
    }

    @Override
    public List<Executor> getExecutors()
    {
        List<Executor> executors = new ArrayList<>();
        Iterable<Executor> executorIterable = executorRepository.findAll();
        for(Executor executor : executorIterable)
        {
            executors.add(executor);
        }
        return executors;
    }

}
