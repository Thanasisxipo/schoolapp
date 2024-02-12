package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.Meeting;

public class MeetingNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public MeetingNotFoundException(Meeting meeting) {
        super("Meeting of teacher with id: " + meeting.getTeacherId() + " with student with id: " + meeting.getStudentId() + " was not found");
    }

    public MeetingNotFoundException(String s) {
        super(s);
    }
}
