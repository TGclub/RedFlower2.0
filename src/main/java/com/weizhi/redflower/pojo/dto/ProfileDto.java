package com.weizhi.redflower.pojo.dto;

public class ProfileDto {

    private String name;

    private String definition;

    private String wx;

    public ProfileDto(){}

    public ProfileDto(String name,String definition,String wx){
        this.name = name;
        this.definition = definition;
        this.wx = wx;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
