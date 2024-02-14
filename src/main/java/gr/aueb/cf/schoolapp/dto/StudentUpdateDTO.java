package gr.aueb.cf.schoolapp.dto;

public class StudentUpdateDTO extends BaseDTO {
    private String firstname;
    private String lastname;
    private String gender;
    private String birthDate;
    private Integer cityId;
    private Integer usernameId;

    public StudentUpdateDTO() {
    }

    public StudentUpdateDTO(String firstname, String lastname, String gender, String birthDate, Integer cityId, Integer usernameId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.cityId = cityId;
        this.usernameId = usernameId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(Integer usernameId) {
        this.usernameId = usernameId;
    }
}
