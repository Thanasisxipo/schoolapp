package gr.aueb.cf.schoolapp.dto;

public class CiyReadOnlyDTO extends BaseDTO {
    private String cityName;

    public CiyReadOnlyDTO() {
    }

    public CiyReadOnlyDTO(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
