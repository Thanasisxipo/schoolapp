package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements ITeacherDAO {
    @Override
    public Teacher insert(Teacher teacher) throws TeacherDAOException {
        String sql = "INSERT INTO TEACHERS (FIRSTNAME, LASTNAME, SSN, SPECIALITY_ID, USER_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String firstname = teacher.getFirstname();
            String lastname = teacher.getLastname();
            Integer ssn = teacher.getSsn();
            Integer specialityId = teacher.getSpecialityId();
            Integer userId = teacher.getUserId();

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, ssn);
            ps.setInt(4, specialityId);
            ps.setInt(5, userId);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return teacher;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new TeacherDAOException("SQL Error in Teacher" + teacher + " insertion");
        }
    }

    @Override
    public Teacher update(Teacher teacher) throws TeacherDAOException {
        String sql = "UPDATE TEACHERS SET FIRSTNAME = ?, LASTNAME = ? , SSN = ?, SPECIALITY_ID = ?, USER_ID = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            int id = teacher.getId();
            String firstname = teacher.getFirstname();
            String lastname = teacher.getLastname();
            Integer ssn = teacher.getSsn();
            Integer specialityId = teacher.getSpecialityId();
            Integer userId = teacher.getUserId();

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, ssn);
            ps.setInt(4, specialityId);
            ps.setInt(5, userId);
            ps.setInt(6, id);

            int n = ps.executeUpdate();
            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Update", JOptionPane.INFORMATION_MESSAGE);
            return teacher;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new TeacherDAOException("SQL Error in Teacher" + teacher + " update");
        }
    }

    @Override
    public void delete(Integer id) throws TeacherDAOException {
        String sql = "DELETE FROM TEACHERS WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int n = ps.executeUpdate();
            if (n != 1) {
                JOptionPane.showMessageDialog(null, "Error", "Delete", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error in deleting Teacher with id: " + id);
        }
    }

    @Override
    public List<Teacher> getByLastname(String lastname) throws TeacherDAOException {
        String sql = "SELECT * FROM TEACHERS WHERE LASTNAME LIKE ?";
        List<Teacher> teachers = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs;

            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getInt("SSN"), rs.getInt("SPECIALITY_ID"), rs.getInt("USER_ID"));
                teachers.add(teacher);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new TeacherDAOException("SQL Error in finding Teachers with lastname: " + lastname);
        }

        return teachers;
    }

    @Override
    public Teacher getById(Integer id) throws TeacherDAOException {
        String sql = "SELECT * FROM TEACHERS WHERE ID = ?";
        Teacher teacher = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                teacher = new Teacher(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getInt("SSN"), rs.getInt("SPECIALITY_ID"), rs.getInt("USER_ID"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new TeacherDAOException("SQL Error in finding Teacher with id: " + id);
        }
        return teacher;
    }
}
