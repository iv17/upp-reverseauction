package com.upp.reverseauction.dto;

import org.activiti.engine.form.FormType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CustomFormType {

    private String name;
    private Map<String, Object> information;

    public CustomFormType() {
    }

    public CustomFormType(String name, Map<String, Object> information) {
        this.name = name;
        this.information = information;
    }

    public CustomFormType(FormType formType) {
        this.name = formType.getName();
        this.information = new HashMap<>();
        this.getInformation(formType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Map<String, Object> information) {
        this.information = information;
    }

    public void getInformation(FormType formType) {
        for (Field field : formType.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                this.information.put(field.getName(), field.get(formType));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
