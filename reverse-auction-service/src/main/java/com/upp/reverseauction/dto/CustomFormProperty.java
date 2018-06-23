package com.upp.reverseauction.dto;

public class CustomFormProperty {

    private String id;
    private String name;
    private boolean readable;
    private boolean writable;
    private boolean required;
    private CustomFormType type;
    private String value;

    public CustomFormProperty() {
    }

    public CustomFormProperty(String id, String name, boolean readable, boolean writable, boolean required, CustomFormType type, String value) {
        this.id = id;
        this.name = name;
        this.readable = readable;
        this.writable = writable;
        this.required = required;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public CustomFormType getType() {
        return type;
    }

    public void setType(CustomFormType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
