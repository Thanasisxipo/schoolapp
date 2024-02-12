package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAOImpl implements ICityDAO {
    @Override
    public City insert(City city) throws CityDAOException {
        String sql = "INSERT INTO CITIES (CITY) VALUES (? ) ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String cityName = city.getCityName();

            ps.setString(1, cityName);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return city;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new CityDAOException("SQL Error in city: " + city + " insertion");
        }
    }

    @Override
    public City update(City city) throws CityDAOException {
        String sql = "UPDATE CITIES SET CITY = ? WHERE ID = ? ";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            String cityName = city.getCityName();
            int id = city.getId();
            ps.setString(1, cityName);
            ps.setInt(2, id);
            int n = ps.executeUpdate();

            if (n != 1) {
                return null;
            }
            JOptionPane.showMessageDialog(null,  n + " row affected", "Insert", JOptionPane.INFORMATION_MESSAGE);
            return city;
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new CityDAOException("SQL Error in City: " + city + " update");
        }
    }

    @Override
    public void delete(Integer id) throws CityDAOException {
        String sql = "DELETE FROM CITIES WHERE ID = ?";

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
            throw new CityDAOException("SQL Error in deleting city with id: " + id);
        }
    }

    @Override
    public City getById(Integer id) throws CityDAOException {
        String sql = "SELECT * FROM CITIES WHERE ID = ?";
        City city = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                city = new City(rs.getInt("ID"), rs.getString("CITY"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new CityDAOException("SQL Error in finding city with id: " + id);
        }
        return city;
    }

    @Override
    public City getByName(String cityName) throws CityDAOException {
        String sql = "SELECT * FROM CITIES WHERE CITY LIKE ?";
        City city = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setString(1, cityName);
            rs = ps.executeQuery();

            if (rs.next()) {
                city = new City(rs.getInt("ID"), rs.getString("CITY"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new CityDAOException("SQL Error in finding city with name: " + cityName);
        }
        return city;
    }

}
