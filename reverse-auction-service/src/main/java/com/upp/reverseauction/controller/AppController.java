package com.upp.reverseauction.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upp.reverseauction.dto.CustomFormProperty;
import com.upp.reverseauction.dto.CustomFormType;
import com.upp.reverseauction.dto.CustomTask;
import com.upp.reverseauction.dto.MessageDTO;
import com.upp.reverseauction.model.PrivateUser;

@RestController
@RequestMapping("api/process")
@CrossOrigin(value = "*")
public class AppController {

    private TaskService taskService;

    private FormService formService;

    private RepositoryService repositoryService;

    private IdentityService identityService;

    @Autowired
    public AppController(final TaskService taskService,
                         final FormService formService,
                         final RepositoryService repositoryService,
                         final IdentityService identityService) {
        this.taskService = taskService;
        this.formService = formService;
        this.repositoryService = repositoryService;
        this.identityService = identityService;
    }

    @GetMapping
    public ResponseEntity getNewInstance() {
        ProcessDefinition pDef = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("auction")
                .latestVersion()
                .singleResult();

        StartFormData formData = formService.getStartFormData(pDef.getId());
        List<FormProperty> formProperties = formData.getFormProperties();

        List<CustomFormProperty> customFormProperties = formProperties.stream()
                .map(p -> new CustomFormProperty(p.getId(), p.getName(), p.isReadable(), p.isWritable(), p.isRequired(),
                        new CustomFormType(p.getType()), p.getValue()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(customFormProperties, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity startNewInstance(@RequestBody Map<String, String> params,
                                           @AuthenticationPrincipal PrivateUser user) {
        ProcessDefinition pDef = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("auction")
                .latestVersion()
                .singleResult();

        identityService.setAuthenticatedUserId(user.getUsername());
        try {
            formService.submitStartFormData(pDef.getId(), params);
            return new ResponseEntity<>(new MessageDTO("New process successfully started"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tasks")
    public ResponseEntity getTasks(@AuthenticationPrincipal PrivateUser user) {
        List<Task> userTasks = taskService.createTaskQuery()
                .taskAssignee(user.getUsername())
                .list();

        List<CustomTask> finalTasks = userTasks
                .stream()
                .map(t -> new CustomTask(t.getId(), t.getFormKey(), t.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(finalTasks, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("tasks/{id}")
    public ResponseEntity getTask(@PathVariable String id,
                                  @AuthenticationPrincipal PrivateUser user) {

        if (!canExecute(id, user.getUsername())) {
            return new ResponseEntity<>(new MessageDTO("Forbidden"), HttpStatus.FORBIDDEN);
        }

        Task task = taskService.createTaskQuery().taskId(id).includeTaskLocalVariables().singleResult();
        Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
        List<FormProperty> formProperties = formService.getTaskFormData(task.getId()).getFormProperties();
        List<CustomFormProperty> customFormProperties = formProperties.stream()
                .map(p -> new CustomFormProperty(p.getId(), p.getName(), p.isReadable(), p.isWritable(), p.isRequired(),
                        new CustomFormType(p.getType()), p.getValue()))
                .collect(Collectors.toList());
        CustomTask customTask = new CustomTask(task.getId(), task.getFormKey(), task.getName(), customFormProperties, taskLocalVariables);
        return new ResponseEntity<>(customTask, HttpStatus.OK);
    }

    @PostMapping("tasks/{id}")
    public ResponseEntity executeTask(@PathVariable String id,
                                      @RequestBody Map<String, String> params,
                                      @AuthenticationPrincipal PrivateUser user) {

        if (canExecute(id, user.getUsername())) {
            if (formService.getTaskFormData(id).getFormProperties().size() == 0) {
                taskService.complete(id);
            } else {
                formService.submitTaskFormData(id, params);
            }
            return new ResponseEntity<>(new MessageDTO("Task successfully executed"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Forbidden"), HttpStatus.FORBIDDEN);
    }

    @GetMapping("tasks/{id}/claim")
    public ResponseEntity claimTask(@PathVariable String id,
                                    @AuthenticationPrincipal PrivateUser user) {
        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean canExecute(String taskId, String username) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();

        return tasks.stream().anyMatch(t -> t.getId().equals(taskId));
    }

    private boolean canClaim(String taskId, String username) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateUser(username)
                .list();

        return tasks.stream().anyMatch(t -> t.getId().equals(taskId));
    }
}
