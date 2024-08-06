package id.tsi.mmw.model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class MasterCustomer {
    @JsonProperty ("CustomerID")
    private String customerID;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
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
    @JsonProperty("DateOfBirth")
    private String dateOfBirth;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("RegistrationDate")
    private String registrationDate;

    public String getCustomerID(){return customerID;}
    public void setCustomerID(String customerID){this.customerID = customerID;}
    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
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
    public String getDateOfBirth(){return dateOfBirth;}
    public void setDateOfBirth(String dateOfBirth){this.dateOfBirth = dateOfBirth;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
    public String getRegistrationDate(){return registrationDate;}
    public void setRegistrationDate(String registrationDate){this.registrationDate = registrationDate;}

}
