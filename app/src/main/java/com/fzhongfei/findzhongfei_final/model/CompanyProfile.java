package com.fzhongfei.findzhongfei_final.model;

import java.util.HashMap;

public class CompanyProfile {
    private String companyId, companyToken, companyName, companyType, companySubType, companyProvince,
            companyCity, companyPhone, companyEmail,
            companyCeo, companyRepresentative, companyLogo, companyLicense,
            companyRepresentativeEmail, companyAddress1, companyAddress2;

    public CompanyProfile(String companyName, String companyType, String companySubType, String companyProvince,
                          String companyCity, String companyPhone, String companyEmail, String companyCeo,
                          String companyRepresentative, String companyRepresentativeEmail, String companyAddress1,
                          String companyAddress2, String companyId, String companyToken,
                          String companyLicense, String companyLogo) {
        this.companyId = companyId;
        this.companyToken = companyToken;
        this.companyLicense = companyLicense;
        this.companyLogo = companyLogo;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companySubType = companySubType;
        this.companyProvince = companyProvince;
        this.companyCity = companyCity;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.companyCeo = companyCeo;
        this.companyRepresentative = companyRepresentative;
        this.companyRepresentativeEmail = companyRepresentativeEmail;
        this.companyAddress1 = companyAddress1;
        this.companyAddress2 = companyAddress2;
    }

    public void SetCompData(HashMap<String, String> compData)
    {
        this.companyId = compData.get("compId");
        this.companyToken = compData.get("compToken");
        this.companyLogo = compData.get("compLogo");
        this.companyLicense = compData.get("compLicense");
        this.companyName = compData.get("compName");
        this.companyType = compData.get("compType");
        this.companySubType = compData.get("compSubType");
        this.companyProvince = compData.get("compProvince");
        this.companyPhone = compData.get("compPhone");
        this.companyCeo = compData.get("compCeo");
        this.companyEmail = compData.get("compEmail");
        this.companyRepresentative = compData.get("compRepName");
        this.companyRepresentativeEmail = compData.get("compRepEmail");
        this.companyAddress1 = compData.get("compAddress1");
        this.companyAddress2 = compData.get("compAddress2");
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
    public void setCompanySubType(String companySubType) {
        this.companySubType = companySubType;
    }
    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }
    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
    public void setCompanyCeo(String companyCeo) {
        this.companyCeo = companyCeo;
    }
    public void setCompanyRepresentative(String companyRepresentative) {
        this.companyRepresentative = companyRepresentative;
    }
    public void setCompanyRepresentativeEmail(String companyRepresentativeEmail) {
        this.companyRepresentativeEmail = companyRepresentativeEmail;
    }
    public void setCompanyAddress1(String companyAddress1) {
        this.companyAddress1 = companyAddress1;
    }
    public void setCompanyAddress2(String companyAddress2) {
        this.companyAddress2 = companyAddress2;
    }

    public String getCompanyName() {
        return companyName;
    }
    public String getCompanyType() {
        return companyType;
    }
    public String getCompanySubType() {
        return companySubType;
    }
    public String getCompanyProvince() {
        return companyProvince;
    }
    public String getCompanyCity() {
        return companyCity;
    }
    public String getCompanyPhone() {
        return companyPhone;
    }
    public String getCompanyEmail() {
        return companyEmail;
    }
    public String getCompanyCeo() {
        return companyCeo;
    }
    public String getCompanyRepresentative() {
        return companyRepresentative;
    }
    public String getCompanyRepresentativeEmail() {
        return companyRepresentativeEmail;
    }
    public String getCompanyAddress1() {
        return companyAddress1;
    }
    public String getCompanyAddress2() {
        return companyAddress2;
    }

}
