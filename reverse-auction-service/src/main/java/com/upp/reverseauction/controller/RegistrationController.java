package com.upp.reverseauction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upp.reverseauction.dto.CustomTask;
import com.upp.reverseauction.dto.RegistrationDetails;
import com.upp.reverseauction.model.BusinessCategory;
import com.upp.reverseauction.service.BusinessCategoryService;

@RestController
@RequestMapping("api/register")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private BusinessCategoryService businessCategoryService;

    private TaskService taskService;

    private RuntimeService runtimeService;

    private FormService formService;

    private RepositoryService repositoryService;

    @Autowired
    public RegistrationController(BusinessCategoryService businessCategoryService,
                                  TaskService taskService,
                                  RuntimeService runtimeService,
                                  FormService formService,
                                  RepositoryService repositoryService) {
        this.businessCategoryService = businessCategoryService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.repositoryService = repositoryService;
    }

    @GetMapping
    public ResponseEntity startRegistrationProcess() {
        ProcessDefinition pdef = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("registration").latestVersion().singleResult();

        StartFormData formData = formService.getStartFormData(pdef.getId());
        List<FormProperty> formProperties = formData.getFormProperties();

        return new ResponseEntity<>(formProperties, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity register(@RequestBody RegistrationDetails registrationDetails) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("registration")
                .latestVersion()
                .singleResult();

        formService.submitStartFormData(procDef.getId(), convertToMap(registrationDetails));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("verify/{id}")
    public ResponseEntity verifyAccount(@PathVariable long id) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("registration")
                .singleResult();

        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(instance.getId())
                .activityId("registrationConfirmation")
                .singleResult();

        Map<String, Object> vars = new HashMap<>();
        vars.put("userId", id);

        runtimeService.signal(execution.getId(), vars);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("task")
    public ResponseEntity getUserTasks(@RequestParam("username") String username) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();

        if (tasks.size() > 0) {
            Task nextTask = tasks.get(0);

            CustomTask t = new CustomTask(nextTask.getId(), nextTask.getFormKey(), nextTask.getName());
            return new ResponseEntity<>(t, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    public ResponseEntity getTaskData(@PathVariable String id) {
        TaskFormData formData = formService.getTaskFormData(id);
        List<FormProperty> props = formData.getFormProperties();

        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    @PutMapping("task/execute/{id}")
    public ResponseEntity executeTask(@PathVariable String id,
            @RequestBody Map<String, String> data) {
        formService.submitTaskFormData(id, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("categories")
    public ResponseEntity getCategories() {
        List<BusinessCategory> categories = this.businessCategoryService.findAll();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    private Map<String, String> convertToMap(RegistrationDetails details) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(details, Map.class);
        return map;
    }
}
