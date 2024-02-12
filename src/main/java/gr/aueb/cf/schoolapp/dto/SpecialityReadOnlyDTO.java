package gr.aueb.cf.schoolapp.dto;

public class SpecialityReadOnlyDTO extends BaseDTO {
    private String speciality;

    public SpecialityReadOnlyDTO() {
    }

    public SpecialityReadOnlyDTO(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
