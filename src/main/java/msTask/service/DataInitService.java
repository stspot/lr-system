package msTask.service;

import jakarta.annotation.PostConstruct;

public interface DataInitService {

    /**
     * Initializes the data for the DataInitService.
     * This method is annotated with @PostConstruct, indicating that it should be executed
     * after the DataInitService bean has been constructed and all dependency injection has been performed.
     * Therefore, this method should be called automatically by the Spring container
     * after the DataInitService bean is instantiated.
     */
    @PostConstruct
    void initData();
}