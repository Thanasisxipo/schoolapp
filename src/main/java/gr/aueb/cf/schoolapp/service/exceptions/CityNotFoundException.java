package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.City;

public class CityNotFoundException extends  Exception {
    private static final long serialVersionUID = 142L;

    public CityNotFoundException(City city) {
        super("Teacher with id: " + city.getId() + " was not found");
    }

    public CityNotFoundException(String s) {
        super(s);
    }
}
