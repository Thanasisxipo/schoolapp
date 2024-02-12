package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialityDAOImpl implements ISpecialityDAO {
    @Override
    public Speciality insert(Speciality speciality) throws SpecialityDAOException {
        String sql = "INSERT INTO SPECIALITIES (SPECIALITY) VALUES (? ) ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String specialityName = speciality.getSpeciality();

            ps.setString(1, specialityName);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return speciality;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Speciality: " + speciality + " insertion");
        }
    }

    @Override
    public Speciality update(Speciality speciality) throws SpecialityDAOException {
        String sql = "UPDATE SPECIALITIES SET SPECIALITY = ? WHERE ID = ? ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String specialityName = speciality.getSpeciality();
            int id = speciality.getId();
            ps.setString(1, specialityName);
            ps.setInt(2, id);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return speciality;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Speciality: " + speciality + " update");
        }
    }

    @Override
    public void delete(Integer id) throws SpecialityDAOException {
        String sql = "DELETE FROM SPECIALITIES WHERE ID = ?";

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
            throw new SpecialityDAOException("SQL Error in deleting speciality with id: " + id);
        }
    }

    @Override
    public Speciality getById(Integer id) throws SpecialityDAOException {
        String sql = "SELECT * FROM SPECIALITIES WHERE ID = ?";
        Speciality speciality = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                speciality = new Speciality(rs.getInt("ID"), rs.getString("SPECIALITY"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new SpecialityDAOException("SQL Error in finding speciality with id: " + id);
        }
        return speciality;
    }

    @Override
    public Speciality getByName(String speciality) throws SpecialityDAOException {
        String sql = "SELECT * FROM SPECIALITIES WHERE SPECIALITY LIKE ?";
        Speciality specialityToReturn = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setString(1, speciality);
            rs = ps.executeQuery();

            if (rs.next()) {
                specialityToReturn = new Speciality(rs.getInt("ID"), rs.getString("SPECIALITY"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new SpecialityDAOException("SQL Error in finding speciality with name: " + speciality);
        }
        return specialityToReturn;
    }
}
