package com.upp.reverseauction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomTask {

    private String formKey;
    private String id;
    private String name;
    private List<CustomFormProperty> props;
    private Map<String, Object> localVariables;

    public CustomTask(String id, String formKey, String name) {
        this.id = id;
        this.formKey = formKey;
        this.name = name;
        this.props = new ArrayList<>();
    }

    public CustomTask(String id, String formKey, String name, List<CustomFormProperty> props) {
        this.formKey = formKey;
        this.id = id;
        this.name = name;
        this.props = props;
    }

    public CustomTask(String id, String formKey, String name, List<CustomFormProperty> props, Map<String, Object> localVariables) {
        this.formKey = formKey;
        this.id = id;
        this.name = name;
        this.props = props;
        this.localVariables = localVariables;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CustomFormProperty> getProps() {
        return props;
    }

    public void setProps(List<CustomFormProperty> props) {
        this.props = props;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariables(Map<String, Object> localVariables) {
        this.localVariables = localVariables;
    }
}
