package gr.aueb.cf.schoolapp.dto;

public class CityInsertDTO extends BaseDTO {
    private String cityName;

    public CityInsertDTO() {
    }

    public CityInsertDTO(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
