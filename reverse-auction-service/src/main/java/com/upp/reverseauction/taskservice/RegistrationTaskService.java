package com.upp.reverseauction.taskservice;

import com.upp.reverseauction.model.BusinessCategory;
import com.upp.reverseauction.model.Company;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.model.TempUser;
import com.upp.reverseauction.repository.BusinessCategoryRepository;
import com.upp.reverseauction.repository.CompanyRepository;
import com.upp.reverseauction.repository.PrivateUserRepository;
import com.upp.reverseauction.service.EmailService;
import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
public class RegistrationTaskService {

    private FormService formService;

    private TaskService taskService;

    private RuntimeService runtimeService;

    private BusinessCategoryRepository businessCategoryRepository;

    private PrivateUserRepository privateUserRepository;

    private CompanyRepository companyRepository;

    private EmailService emailService;

    @Autowired
    public RegistrationTaskService(final FormService formService,
                                   final TaskService taskService,
                                   final RuntimeService runtimeService,
                                   final PrivateUserRepository privateUserRepository,
                                   final BusinessCategoryRepository businessCategoryRepository,
                                   final EmailService emailService,
                                   final CompanyRepository companyRepository) {
        this.formService = formService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.privateUserRepository = privateUserRepository;
        this.businessCategoryRepository = businessCategoryRepository;
        this.emailService = emailService;
        this.companyRepository = companyRepository;
    }

    public TempUser temporaryPersistData(String username, String name, String email, String password, String address,
                                         String place, String zipCode, String lat, String lng) {
        TempUser tempUser = new TempUser();
        tempUser.setName(name);
        tempUser.setUsername(username);
        tempUser.setEmail(email);
        tempUser.setPassword(password);
        tempUser.setAddress(address);
        tempUser.setPlace(place);
        tempUser.setZipCode(zipCode);
        tempUser.setLat(lat);
        tempUser.setLng(lng);

        return tempUser;
    }

    public void persistData(TempUser tempUser) {
        PrivateUser user = new PrivateUser();
        user.setName(tempUser.getName());
        user.setZipCode(tempUser.getZipCode());
        user.setPlace(tempUser.getPlace());
        user.setAddress(tempUser.getAddress());
        user.setEmail(tempUser.getEmail());
        user.setUsername(tempUser.getUsername());
        user.setPassword(tempUser.getPassword());
        user.setLat(tempUser.getLat());
        user.setLng(tempUser.getLng());
        user.setConfirmed(false);

        privateUserRepository.save(user);
    }


    public boolean checkData(String username, String email) {
        PrivateUser userByUsername = this.privateUserRepository.findByUsername(username);
        PrivateUser userByEmail = this.privateUserRepository.findByEmail(email);

        return userByUsername == null && userByEmail == null;
    }

    public String notifyAboutInvalidData() {
        //FIX ME: try with web sockets
        return "Korisničko ime/email već postoji u sistemu";
    }

    public void deleteUser(String username) {
        PrivateUser user = this.privateUserRepository.findByUsername(username);
        this.privateUserRepository.delete(user);

        if (user.getCompany() != null)
            this.companyRepository.delete(user.getCompany());
    }

    public void activateUser(long userId) {
        PrivateUser user = this.privateUserRepository.findById(userId);
        user.setConfirmed(true);

        this.privateUserRepository.save(user);
    }

    public void getCategories(DelegateTask task) {
        formService.getTaskFormData(task.getId());
        Map<String, Object> variables = task.getVariables();
        Task t = taskService.createTaskQuery().taskId(task.getId()).includeProcessVariables().singleResult();
        Map<String, Object> notTaskVariables = t.getProcessVariables();
        List<Long> diff = variables.keySet().stream()
                .filter(v -> !notTaskVariables.containsKey(v) && !v.equals("distance"))
                .map(v -> Long.parseLong(variables.get(v).toString()))
                .collect(Collectors.toList());

        List<BusinessCategory> categories = this.businessCategoryRepository.findByIdIsIn(diff);

        task.getExecution().setVariable("categories", categories);
    }

    public void updateCompany(List<BusinessCategory> categories, String username, String distance) {
        PrivateUser user = this.privateUserRepository.findByUsername(username);
        Company company = new Company();
        company.setAgent(user);
        company.setBusinessCategory(categories);
        company.setDistance(Float.valueOf(distance));

        companyRepository.save(company);
        user.setCompany(company);
        privateUserRepository.save(user);
    }
}
