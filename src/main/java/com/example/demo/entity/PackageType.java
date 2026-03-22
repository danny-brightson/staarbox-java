package com.example.demo.entity;




import jakarta.persistence.*;



@Entity
@Table(name = "package_types")
public class PackageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "PackageDetailsId")
    private Integer packageDetailsId;

    @Column(name = "IsPregnant")
    private Boolean isPregnant;

    @Column(name = "IsAllergic")
    private Boolean isAllergic;

    @Column(name = "IsCustomized")
    private Boolean isCustomized;

    @Column(name = "IsCommon")
    private Boolean isCommon;

    // Constructors
    public PackageType() {}

    public PackageType(Integer id, Integer packageDetailsId, Boolean isPregnant, Boolean isAllergic,
                       Boolean isCustomized, Boolean isCommon) {
        this.id = id;
        this.packageDetailsId = packageDetailsId;
        this.isPregnant = isPregnant;
        this.isAllergic = isAllergic;
        this.isCustomized = isCustomized;
        this.isCommon = isCommon;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageDetailsId() {
        return packageDetailsId;
    }

    public void setPackageDetailsId(Integer packageDetailsId) {
        this.packageDetailsId = packageDetailsId;
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

    public Boolean getIsCustomized() {
        return isCustomized;
    }

    public void setIsCustomized(Boolean isCustomized) {
        this.isCustomized = isCustomized;
    }

    public Boolean getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(Boolean isCommon) {
        this.isCommon = isCommon;
    }
}

