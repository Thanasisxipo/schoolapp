package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.User;

public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 143L;

    public UserNotFoundException(User user) {
        super("User with id: " + user.getId() + " was not found");
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
