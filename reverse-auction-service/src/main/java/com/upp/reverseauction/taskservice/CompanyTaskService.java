package com.upp.reverseauction.taskservice;

import com.upp.reverseauction.model.BusinessCategory;
import com.upp.reverseauction.model.Company;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.model.ProcurementOffer;
import com.upp.reverseauction.repository.BusinessCategoryRepository;
import com.upp.reverseauction.repository.CompanyRepository;
import com.upp.reverseauction.repository.PrivateUserRepository;
import com.upp.reverseauction.util.Utils;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyTaskService {

    private BusinessCategoryRepository businessCategoryRepository;

    private CompanyRepository companyRepository;

    private PrivateUserRepository privateUserRepository;

    @Autowired
    public CompanyTaskService(final BusinessCategoryRepository businessCategoryRepository,
                              final CompanyRepository companyRepository,
                              final PrivateUserRepository privateUserRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
        this.companyRepository = companyRepository;
        this.privateUserRepository = privateUserRepository;
    }

    public void formCompanyList(long maxOffers, String category, String client, DelegateExecution execution) {
        PrivateUser user = privateUserRepository.findByUsername(client);
        BusinessCategory bc = businessCategoryRepository.findByName(category);
        List<Company> companies = companyRepository.findAllByCategory(bc.getId());

        if ((Double) execution.getVariable("numOfExecution") > 1) {
            ArrayList<ProcurementOffer> acceptedOffers = (ArrayList<ProcurementOffer>) execution.getVariable("acceptedOffers");
            ArrayList<ProcurementOffer> withdrawnOffers = (ArrayList<ProcurementOffer>) execution.getVariable("withdrawnOffers");
            ArrayList<ProcurementOffer> missedDeadlineOffers = (ArrayList<ProcurementOffer>) execution.getVariable("missedDeadlineOffers");

            companies = companies.stream()
                    .filter(c -> !acceptedOffers.stream().anyMatch(o -> o.getOwner().getId().equals(c.getId())))
                    .filter(c -> !withdrawnOffers.stream().anyMatch(o -> o.getOwner().getId().equals(c.getId())))
                    .filter(c -> !missedDeadlineOffers.stream().anyMatch(o -> o.getOwner().getId().equals(c.getId())))
                    .collect(Collectors.toList());

            resetOffers(execution);
        }

        List<Company> companiesWithGoodDistance = companies.stream()
                .filter(c -> Utils.distance(c.getAgent().getLat(), user.getLat(),
                        c.getAgent().getLng(), user.getLng()) <= c.getDistance())
                .collect(Collectors.toList());

        Collections.shuffle(companiesWithGoodDistance);

        if (companiesWithGoodDistance.size() > maxOffers) {
            companiesWithGoodDistance = companiesWithGoodDistance.subList(0, (int) maxOffers);
        }

        execution.setVariable("candidateCompanies", companiesWithGoodDistance);
    }

    public List<String> getCandidateAgents(List<Company> candidateCompanies) {
        List<String> agents = candidateCompanies.stream()
                .map(c -> c.getAgent().getUsername())
                .collect(Collectors.toList());

        return agents;
    }

    public void filterCompanies(DelegateExecution execution) {
        ArrayList<ProcurementOffer> acceptedOffers = (ArrayList<ProcurementOffer>) execution.getVariable("acceptedOffers");
        ArrayList<ProcurementOffer> withdrawnOffers = (ArrayList<ProcurementOffer>) execution.getVariable("withdrawnOffers");
        ArrayList<ProcurementOffer> missedDeadlineOffers = (ArrayList<ProcurementOffer>) execution.getVariable("missedDeadlineOffers");
        String client = (String) execution.getVariable("client");
        PrivateUser user = privateUserRepository.findByUsername(client);

        String category = (String) execution.getVariable("category");
        BusinessCategory bc = businessCategoryRepository.findByName(category);

        List<Company> companies = companyRepository.findAllByCategory(bc.getId());

        List<Company> filteredCompanies = companies.stream()
                .filter(c -> !acceptedOffers.stream().anyMatch(o -> o.getOwner().getId().equals(c.getId())))
                .filter(c -> !withdrawnOffers.stream().anyMatch(o -> o.getOwner().getId().equals(c.getId())))
                .collect(Collectors.toList());

        List<Company> companiesWithGoodDistance = filteredCompanies.stream()
                .filter(c -> Utils.distance(c.getAgent().getLat(), user.getLat(),
                        c.getAgent().getLng(), user.getLng()) <= c.getDistance())
                .collect(Collectors.toList());


        long maxOffers = (long) execution.getVariable("maxOffers");
        if (companiesWithGoodDistance.size() > maxOffers) {
            companiesWithGoodDistance = companiesWithGoodDistance.subList(0, (int) maxOffers);
        }

        execution.setVariable("candidateCompanies", companiesWithGoodDistance);

    }

    public List<BusinessCategory> getAllBusinessCategories() {
        List<BusinessCategory> categories = this.businessCategoryRepository.findAll();
        return categories;
    }

    public void resetOffers(DelegateExecution execution) {
        execution.setVariable("acceptedOffers", new ArrayList<ProcurementOffer>());
        execution.setVariable("withdrawnOffers", new ArrayList<ProcurementOffer>());
        execution.setVariable("missedDeadlineOffers", new ArrayList<ProcurementOffer>());
    }

}
