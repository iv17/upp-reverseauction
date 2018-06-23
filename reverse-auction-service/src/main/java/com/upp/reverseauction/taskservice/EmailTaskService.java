package com.upp.reverseauction.taskservice;

import com.upp.reverseauction.model.Company;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.repository.PrivateUserRepository;
import com.upp.reverseauction.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmailTaskService {

    private PrivateUserRepository privateUserRepository;

    private EmailService emailService;

    @Autowired
    public EmailTaskService(final PrivateUserRepository privateUserRepository,
                            final EmailService emailService) {
        this.privateUserRepository = privateUserRepository;
        this.emailService = emailService;
    }

    public String sendConfirmationMail(String email) {
        PrivateUser user = this.privateUserRepository.findByEmail(email);
        final String subject = "Verifikacija naloga";
        final String link = String.format("http://localhost:4200/verify/%d", user.getId());
        final String messageText = String.format("Uspešno ste se registrovali.\n\n%s.\n\nMolimo Vas da klikom na link iznad potvrdite svoju registraciju.", link);
        emailService.sendMessage(email, subject, messageText);

        return link;
    }

    public void sendEmailForProcurementRequestCancelation(String client) {
        PrivateUser user = privateUserRepository.findByUsername(client);
        final String subject = "Zahtev za nabavku je neuspešno završen";
        final String messageText = "Vaš zahtev za nabavkom robe trenutno ne može biti izvršen, " +
                "jer ne postoje kompanije koje vrše traženu kategoriju posla";
        emailService.sendMessage(user.getEmail(), subject, messageText);
    }

    public void sendEmailForLessCompanies(String client) {
        PrivateUser user = privateUserRepository.findByUsername(client);
        final String subject = "Zahtev za nabavku je neuspešno završen";
        final String messageText = "Broj kompanija koje izvršavaju traženu kategoriju posla je manji od očekivanog." +
                "Molimo Vas, popunite formu za odluku da li želite ipak nastaviti sa radom";
        emailService.sendMessage(user.getEmail(), subject, messageText);
    }

    public void sendEmailForProcurementRequestToAgent(Company company, String category,
                                                      String description, long maxPrice, Date offersDeadline, Date procurementDeadline) {
        String formattedOffers = new SimpleDateFormat("yyyy-MM-dd").format(offersDeadline);
        String formattedProcurement = new SimpleDateFormat("yyyy-MM-dd").format(procurementDeadline);
        PrivateUser user = company.getAgent();
        final String subject = "Nova ponuda";
        final String messageText = "Dobili ste novu ponudu.Stavke ponude su:\n";
        final String offer = String.format("Kategorija: %s\nOpis: %s\n" +
                "Cena: %d\nRok za podnosenje ponuda: %s\nRok za izvršenje posla: %s", category, description,
                maxPrice, formattedOffers, formattedProcurement);

        emailService.sendMessage(user.getEmail(), subject, messageText + offer);
    }

    public void sendEmailForNoneOffer(String client) {
        PrivateUser user = privateUserRepository.findByUsername(client);
        final String subject = "Broj ponuda";
        final String messageText = "Nema nijedne pristigle ponude." +
                "Molimo Vas, popunite odluku da li zelite da produzite rok";
        emailService.sendMessage(user.getEmail(), subject, messageText);
    }

    public void sendEmailForLessOffers(String client) {
        PrivateUser user = privateUserRepository.findByUsername(client);
        final String subject = "Broj ponuda";
        final String messageText = "Broj ponuda je manji od navedenog. " +
                "Molimo Vas, popunite odluku da li želite da produžite rok";
        emailService.sendMessage(user.getEmail(), subject, messageText);
    }

    public void sendEmailConfirmationToChosenAgent(String chosenAgent) {
        PrivateUser user = privateUserRepository.findByUsername(chosenAgent);
        final String subject = "Potvrda o prihvatanju ponude";
        final String messageText = "Vaša ponuda je prihvaćena. " +
                "Molimo Vas, popunite formu za početak izvršavanja";
        emailService.sendMessage(user.getEmail(), subject, messageText);
    }
}
