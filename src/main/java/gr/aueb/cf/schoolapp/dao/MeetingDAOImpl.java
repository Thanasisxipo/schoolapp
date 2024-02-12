package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.MeetingDAOException;
import gr.aueb.cf.schoolapp.model.Meeting;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import gr.aueb.cf.schoolapp.service.util.DateUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MeetingDAOImpl implements IMeetingDAO {
    @Override
    public Meeting insert(Meeting meeting) throws MeetingDAOException {
        String sql = "INSERT INTO MEETINGS (MEETING_ROOM, MEETING_DATE) VALUES (? , ?) ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String meetingRoom = meeting.getMeetingRoom();
            String meetingDate = DateUtil.toSQLDateString(DateUtil.toSQLDate(DateUtil.toDate(meeting.getMeetingDate())));

            ps.setString(1, meetingRoom);
            ps.setString(2, meetingDate);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return meeting;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new MeetingDAOException("SQL Error in Meeting: " + meeting + " insertion");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Meeting update(Meeting meeting) throws MeetingDAOException {
        String sql = "UPDATE MEETINGS SET MEETING_ROOM = ?, MEETING_DATE = ? WHERE TEACHER_ID = ? AND STUDENT_ID = ? ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            int teacherId = meeting.getTeacherId();
            int studentId = meeting.getStudentId();
            String meetingRoom = meeting.getMeetingRoom();
            String meetingDate = DateUtil.toSQLDateString(DateUtil.toSQLDate(DateUtil.toDate(meeting.getMeetingDate())));

            ps.setString(1, meetingRoom);
            ps.setString(2, meetingDate);
            ps.setInt(3, teacherId);
            ps.setInt(4, studentId);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Update", JOptionPane.INFORMATION_MESSAGE);
            return meeting;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new MeetingDAOException("SQL Error in Meeting: " + meeting + " update");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer teacherId, Integer studentId) throws MeetingDAOException {
        String sql = "DELETE FROM MEETINGS WHERE TEACHER_ID = ? AND STUDENT_ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ps.setInt(2, studentId);

            int n = ps.executeUpdate();
            if (n != 1) {
                JOptionPane.showMessageDialog(null, "Error", "Delete", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MeetingDAOException("SQL Error in deleting meeting with teacherid: " + teacherId + " and studentid: " + studentId);
        }
    }

    @Override
    public Meeting getByTeacherAndStudentId(Integer teacherId, Integer studentId) throws MeetingDAOException {
            String sql = "SELECT * FROM MEETINGS WHERE TEACHER_ID = ? AND STUDENT_ID = ?";
            Meeting meeting = null;

            try (Connection connection = DBUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql);
            ) {
                ResultSet rs;
                ps.setInt(1, teacherId);
                ps.setInt(2, studentId);
                rs = ps.executeQuery();

                if (rs.next()) {
                    meeting = new Meeting(rs.getInt("TEACHER_ID"), rs.getInt("STUDENT_ID"), rs.getString("MEETING_ROOM"), rs.getString("MEETING_DATE"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new MeetingDAOException("SQL Error in finding meeting with teacherid: " + teacherId + " and studentid: " + studentId);


            }
            return meeting;
    }

    @Override
    public List<Meeting> getByTeacherId(Integer teacherId) throws MeetingDAOException {
        String sql = "SELECT * FROM MEETINGS WHERE TEACHER_ID = ?";
        List<Meeting> meetings = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs;

            ps.setInt(1, teacherId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Meeting meeting = new Meeting(rs.getInt("TEACHER_ID"), rs.getInt("STUDENT_ID"), rs.getString("MEETING_ROOM"), rs.getString("MEETING_DATE"));
                meetings.add(meeting);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new MeetingDAOException("SQL Error in finding meetings with teacherid: " + teacherId);

        }

        return meetings;
    }

    @Override
    public List<Meeting> getByStudentId(Integer studentId) throws MeetingDAOException {
        String sql = "SELECT * FROM MEETINGS WHERE STUDENT_ID = ?";
        List<Meeting> meetings = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs;

            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Meeting meeting = new Meeting(rs.getInt("TEACHER_ID"), rs.getInt("STUDENT_ID"), rs.getString("MEETING_ROOM"), rs.getString("MEETING_DATE"));
                meetings.add(meeting);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new MeetingDAOException("SQL Error in finding meetings with studentid: " + studentId);

        }

        return meetings;
    }


    @Override
    public List<Meeting> getByDate(String date) throws MeetingDAOException {
        String sql = "SELECT * FROM MEETINGS WHERE MEETING_DATE = ?";
        List<Meeting> meetings = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs;
            String meetingDate = DateUtil.toSQLDateString(DateUtil.toSQLDate(DateUtil.toDate(date)));

            ps.setString(1, meetingDate);
            rs = ps.executeQuery();

            while (rs.next()) {
                Meeting meeting = new Meeting(rs.getInt("TEACHER_ID"), rs.getInt("STUDENT_ID"), rs.getString("MEETING_ROOM"), rs.getString("MEETING_DATE"));
                meetings.add(meeting);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new MeetingDAOException("SQL Error in finding meetings on date: " + date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return meetings;
    }

}
