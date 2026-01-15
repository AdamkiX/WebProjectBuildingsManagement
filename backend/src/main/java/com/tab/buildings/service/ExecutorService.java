package com.tab.buildings.service;
import com.tab.buildings.entity.Executor;
import java.util.List;

public interface ExecutorService {
    /**
     * Function to add new Executor to table
     * @param executor variable to add
     * @return variable to check if its added right
     */
    Executor addExecutor (Executor executor);
		public List<Executor> getExecutorsByWorkType(Integer work_type_id);
    public List<Executor> getExecutors();

}
