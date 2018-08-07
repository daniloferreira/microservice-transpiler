package com.example.bpmpoc;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessEndpoint {

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/start-process")
    public String startProcess() {

        runtimeService.startProcessInstanceByKey("Meals.UserCreation");

        return "Process started. Number of currently running"
                + "process instances = "
                + runtimeService.createProcessInstanceQuery().count();
    }

}
