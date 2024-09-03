package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class MasterCompany {
    @JsonProperty("CompanyID")
    private String companyID;
    @JsonProperty("CompanyName")
    private String companyName;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("City")
    private String city;
    @JsonProperty("State")
    private String state;
    @JsonProperty("PostalCode")
    private String postalCode;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("Email")
    private String email;

    public String getCompanyID(){return companyID;}
    public void setCompanyID(String companyID){this.companyID = companyID;}
    public String getCompanyName(){return companyName;}
    public void setCompanyName(String companyName){this.companyName = companyName;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getCity(){return city;}
    public void setCity(String city){this.city = city;}
    public String getState(){return state;}
    public void setState(String state){this.state = state;}
    public String getPostalCode(){return postalCode;}
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    public String getCountry(){return country;}
    public void setCountry(String country){this.country = country;}
    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
}
