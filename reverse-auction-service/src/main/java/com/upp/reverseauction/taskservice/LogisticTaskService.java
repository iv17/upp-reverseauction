package com.upp.reverseauction.taskservice;

import com.upp.reverseauction.model.Company;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.model.ProcurementOffer;
import com.upp.reverseauction.repository.CompanyRepository;
import com.upp.reverseauction.repository.PrivateUserRepository;
import com.upp.reverseauction.util.Utils;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.pvm.PvmProcessElement;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogisticTaskService {

    private PrivateUserRepository privateUserRepository;

    private TaskService taskService;

    private ManagementService managementService;

    private CompanyRepository companyRepository;

    @Autowired
    public LogisticTaskService(final PrivateUserRepository privateUserRepository,
                               final TaskService taskService,
                               final CompanyRepository companyRepository) {
        this.privateUserRepository = privateUserRepository;
        this.taskService = taskService;
        this.companyRepository = companyRepository;
    }

    public void collectOffer(DelegateTask task, DelegateExecution execution) {
        String assignee = task.getAssignee();
        PrivateUser user = privateUserRepository.findByUsername(assignee);
        Company company = companyRepository.findCompanyByAgent(user.getId());

        boolean isWithdrawal = (boolean) execution.getVariable("isWithdrawal");
        float price = Float.parseFloat(execution.getVariable("charge").toString());
        Date performDueDate = (Date) execution.getVariable("performDueDate");

        if (isWithdrawal) {
            if (execution.getVariable("withdrawnOffers") == null) {
                execution.setVariable("withdrawnOffers", new ArrayList<ProcurementOffer>());
            }
            ArrayList<ProcurementOffer> offers = (ArrayList<ProcurementOffer>) execution.getVariable("withdrawnOffers");
            offers.add(new ProcurementOffer(isWithdrawal, company));
        } else {
            if (execution.getVariable("acceptedOffers") == null) {
                execution.setVariable("acceptedOffers", new ArrayList<ProcurementOffer>());
            }
            ArrayList<ProcurementOffer> offers = (ArrayList<ProcurementOffer>) execution.getVariable("acceptedOffers");
            offers.add(new ProcurementOffer(price, performDueDate, company));
        }
    }

    public void collectOffers(DelegateExecution execution) {
        if (execution.getVariable("acceptedOffers") == null) {
            execution.setVariable("acceptedOffers", new ArrayList<ProcurementOffer>());
        }
    }

    public void rejectMissedDeadlineOffers(DelegateExecution execution, String agent) {
        String currentActivityId = execution.getCurrentActivityId();
        String currentActivityName = execution.getCurrentActivityName();
        PrivateUser user = privateUserRepository.findByUsername(agent);
        Company company = companyRepository.findCompanyByAgent(user.getId());

        if (execution.getVariable("missedDeadlineOffers") == null) {
            execution.setVariable("missedDeadlineOffers", new ArrayList<ProcurementOffer>());
        }
        ArrayList<ProcurementOffer> offers = (ArrayList<ProcurementOffer>) execution.getVariable("missedDeadlineOffers");
        offers.add(new ProcurementOffer(true, company));

        if (execution.getVariable("withdrawnOffers") == null) {
            execution.setVariable("withdrawnOffers", new ArrayList<ProcurementOffer>());
        }
    }

    public void rankOffers(DelegateExecution execution) {
        List<ProcurementOffer> tempOffers = (ArrayList<ProcurementOffer>) execution.getVariable("acceptedOffers");

        Comparator<ProcurementOffer> comparator = Comparator.comparing(ProcurementOffer::getCharge);
        comparator = comparator.thenComparing(Comparator.comparing(ProcurementOffer::getPerformDueDate));

        List<ProcurementOffer> sortedOffers = tempOffers.stream().sorted(comparator).collect(Collectors.toList());
        execution.setVariable("finalOffers", sortedOffers);
    }

    public void updateOffersDeadline(DelegateTask task, DelegateExecution execution) {
        execution.setVariable("offersDeadline", task.getVariable("newOffersDeadline"));
    }

    public void setFinalOffers(DelegateTask task, DelegateExecution execution) {
        task.setVariableLocal("offers", execution.getVariable("finalOffers"));
    }

    public void setChosenCompany(DelegateTask task, DelegateExecution execution) {
        long companyId = (long) task.getVariable("chosenCompanyId");
        boolean isOneAgentChosen = companyId != -1;
        execution.setVariable("isOneAgentChosen", isOneAgentChosen);

        if (companyId != -1) {
            Company company = companyRepository.findById(companyId);
            execution.setVariable("chosenAgent", company.getAgent().getUsername());
        }
    }

    public void setNumOfExecution(DelegateExecution execution) {
        execution.setVariable("numOfExecution", 1d);
    }

    public void setISODeadlines(DelegateExecution execution) {
        Date offersDeadline = (Date) execution.getVariable("offersDeadline");
        Date procurementDeadline = (Date) execution.getVariable("procurementDeadline");

        execution.setVariable("offersDeadlineISO", Utils.toISOString(offersDeadline));
        execution.setVariable("procurementDeadlineISO", Utils.toISOString(procurementDeadline));
    }
}
