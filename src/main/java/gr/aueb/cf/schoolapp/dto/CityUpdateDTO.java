package gr.aueb.cf.schoolapp.dto;

public class CityUpdateDTO extends BaseDTO {
    private String cityName;

    public CityUpdateDTO() {
    }

    public CityUpdateDTO(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
