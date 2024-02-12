package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolapp.model.Speciality;

public interface ISpecialityDAO {
    Speciality insert(Speciality speciality) throws SpecialityDAOException;
    Speciality update(Speciality speciality) throws SpecialityDAOException;
    void delete(Integer id) throws SpecialityDAOException;
    Speciality getById(Integer id) throws SpecialityDAOException;
    Speciality getByName(String speciality) throws SpecialityDAOException;
}
