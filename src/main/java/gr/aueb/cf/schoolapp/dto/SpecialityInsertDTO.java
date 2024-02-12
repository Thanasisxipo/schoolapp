package gr.aueb.cf.schoolapp.dto;

public class SpecialityInsertDTO extends BaseDTO {
    private String speciality;

    public SpecialityInsertDTO() {
    }

    public SpecialityInsertDTO(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
