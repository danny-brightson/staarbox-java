package com.example.demo.dto;

public class HealthUpdateRequestDto {

    private Long customerId;
    private Boolean isPregnant;
    private Boolean isAllergic;
    private String allergyDetails;
    private Boolean isDiabetic;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public Boolean getIsAllergic() {
        return isAllergic;
    }

    public void setIsAllergic(Boolean isAllergic) {
        this.isAllergic = isAllergic;
    }

    public String getAllergyDetails() {
        return allergyDetails;
    }

    public void setAllergyDetails(String allergyDetails) {
        this.allergyDetails = allergyDetails;
    }

    public Boolean getIsDiabetic() {
        return isDiabetic;
    }

    public void setIsDiabetic(Boolean isDiabetic) {
        this.isDiabetic = isDiabetic;
    }
}