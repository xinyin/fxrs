package com.yzj.jep.app.sys.dto;

public class PermitTypeDto {
    private String name;
    private String value;
    private Boolean state;


    public PermitTypeDto(String name, String value, Boolean state) {
        super();
        this.name = name;
        this.value = value;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
