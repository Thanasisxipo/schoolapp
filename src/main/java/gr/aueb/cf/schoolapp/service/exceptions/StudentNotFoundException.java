package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.Student;

public class StudentNotFoundException extends Exception {
    private static final long serialVersionUID = 146L;

    public StudentNotFoundException(Student student) {
        super("Student with id: " + student.getId() + " was not found");
    }

    public StudentNotFoundException(String s) {
        super(s);
    }
}
