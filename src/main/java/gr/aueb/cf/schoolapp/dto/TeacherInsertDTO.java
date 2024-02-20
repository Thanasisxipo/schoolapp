package gr.aueb.cf.schoolapp.dto;

public class TeacherInsertDTO extends BaseDTO {
    private Integer ssn;
    private String firstname;
    private String lastname;
    private Integer specialityId;
    private Integer usernameId;

    public TeacherInsertDTO() {
    }

    public TeacherInsertDTO(Integer ssn, String firstname, String lastname, Integer specialityId, Integer usernameId) {
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialityId = specialityId;
        this.usernameId = usernameId;
    }

    public Integer getSsn() {
        return ssn;
    }

    public void setSsn(Integer ssn) {
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

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Integer getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(Integer usernameId) {
        this.usernameId = usernameId;
    }
}
