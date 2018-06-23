package com.upp.reverseauction;


import javax.sql.DataSource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReverseAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReverseAuctionApplication.class, args);
    }

    @Bean
    public DataSource database() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/auction?characterEncoding=UTF-8")
                .username("root")
                .password("root")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }

    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return () -> {

            if (identityService.createGroupQuery().groupName("privateUser") == null) {
                Group privateUsers = identityService.newGroup("privateUser");
                privateUsers.setName("privateUsers");
                privateUsers.setType("customer");
                identityService.saveGroup(privateUsers);

                Group agent = identityService.newGroup("agent");
                agent.setName("agent");
                agent.setType("agent");
                identityService.saveGroup(agent);
            }
        };
    }
}
