package com.example.demo.dto;

public class VersionResponse {

    private Boolean isForceUpdate;
    private Boolean isSoftUpdate;

    public VersionResponse(Boolean isForceUpdate, Boolean isSoftUpdate) {
        this.isForceUpdate = isForceUpdate;
        this.isSoftUpdate = isSoftUpdate;
    }

    public Boolean getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public Boolean getIsSoftUpdate() {
        return isSoftUpdate;
    }

    public void setIsSoftUpdate(Boolean isSoftUpdate) {
        this.isSoftUpdate = isSoftUpdate;
    }
}