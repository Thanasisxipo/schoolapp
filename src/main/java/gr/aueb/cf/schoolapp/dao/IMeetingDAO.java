package gr.aueb.cf.schoolapp.dao;



import gr.aueb.cf.schoolapp.dao.exceptions.MeetingDAOException;
import gr.aueb.cf.schoolapp.model.Meeting;

import java.util.List;

public interface IMeetingDAO {
    Meeting insert(Meeting meeting) throws MeetingDAOException;
    Meeting update(Meeting meeting) throws MeetingDAOException;
    void delete(Integer teacherId, Integer studentId) throws MeetingDAOException;
    Meeting getByTeacherAndStudentId(Integer teacherId, Integer studentId) throws MeetingDAOException;
    List<Meeting> getByTeacherId(Integer teacherId) throws MeetingDAOException;
    List<Meeting> getByStudentId(Integer studentId) throws MeetingDAOException;
    List<Meeting> getByDate(String date) throws MeetingDAOException;
}
