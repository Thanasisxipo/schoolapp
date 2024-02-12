package gr.aueb.cf.schoolapp.dto;

public class TeacherReadOnlyDTO extends BaseDTO {
    private String ssn;
    private String firstname;
    private String lastname;
    private String speciality;
    private String username;

    public TeacherReadOnlyDTO() {
    }

    public TeacherReadOnlyDTO(String ssn, String firstname, String lastname, String speciality, String username) {
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.speciality = speciality;
        this.username = username;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
