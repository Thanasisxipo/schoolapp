package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import gr.aueb.cf.schoolapp.service.util.DateUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO{
    @Override
    public Student insert(Student student) throws StudentDAOException {
        String sql = "INSERT INTO STUDENTS (FIRSTNAME, LASTNAME, GENDER, BIRTH_DATE, CITY_ID, USER_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String firstname = student.getFirstname();
            String lastname = student.getLastname();
            String gender = student.getGender();
            java.util.Date birthDate = student.getBirthDate();
            Integer cityId = student.getCityId();
            Integer userId = student.getUserId();


            java.sql.Date sqlBirthDate = DateUtil.toSQLDate(birthDate);


            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, gender);
            ps.setDate(4, sqlBirthDate);
            ps.setInt(5, cityId);
            ps.setInt(6, userId);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return student;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new StudentDAOException("SQL Error in Student: " + student + " insertion");
        }
    }

    @Override
    public Student update(Student student) throws StudentDAOException {
        String sql = "UPDATE STUDENTS SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, BIRTH_DATE = ?, CITY_ID = ?, USER_ID = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            int id = student.getId();
            String firstname = student.getFirstname();
            String lastname = student.getLastname();
            String gender = student.getGender();
            java.util.Date birthDate = student.getBirthDate();
            Integer cityId = student.getCityId();
            Integer userId = student.getUserId();

            java.sql.Date sqlBirthDate = DateUtil.toSQLDate(birthDate);

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, gender);
            ps.setDate(4, sqlBirthDate);
            ps.setInt(5, cityId);
            ps.setInt(6, userId);
            ps.setInt(7, id);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Update", JOptionPane.INFORMATION_MESSAGE);
            return student;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new StudentDAOException("SQL Error in Student: " + student + " update");
        }
    }

    @Override
    public void delete(Integer id) throws StudentDAOException {
        String sql = "DELETE FROM STUDENTS WHERE ID = ?";

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
            throw new StudentDAOException("SQL Error in deleting Student with id: " + id);
        }
    }

    @Override
    public Student getById(Integer id) throws StudentDAOException {
        String sql = "SELECT * FROM STUDENTS WHERE ID = ?";
        Student student = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("GENDER"), rs.getDate("BIRTH_DATE"), rs.getInt("CITY_ID"), rs.getInt("USER_ID"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new StudentDAOException("SQL Error in finding Student with id: " + id);

        }
        return student;
    }

    @Override
    public List<Student> getByLastName(String lastname) throws StudentDAOException {
        String sql = "SELECT * FROM STUDENTS WHERE LASTNAME LIKE ?";
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs;

            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("GENDER"), rs.getDate("BIRTH_DATE"), rs.getInt("CITY_ID"), rs.getInt("USER_ID"));
                students.add(student);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new StudentDAOException("SQL Error in finding Students with lastname: " + lastname);

        }

        return students;
    }
}
