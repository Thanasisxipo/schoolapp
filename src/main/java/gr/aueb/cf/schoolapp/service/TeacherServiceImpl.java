package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl implements ITeacherService {
    private final ITeacherDAO teacherDAO;



    public TeacherServiceImpl(ITeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }


    @Override
    public Teacher insertTeacher(TeacherInsertDTO dto) throws TeacherDAOException {
        if (dto == null) return null;

        try {
            return Optional.of(teacherDAO.insert(map(dto)))
                    .orElseThrow(() -> new RuntimeException(""));
        } catch (TeacherDAOException e) {
            e.printStackTrace();
            throw e;
        } catch (SpecialityDAOException | UserDAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher updateTeacher(TeacherUpdateDTO dto) throws TeacherNotFoundException, TeacherDAOException {
        if (dto == null) return null;
        Teacher teacher = null;
        try {
            teacher = map(dto);
            Teacher existingTeacher = Optional.of(teacherDAO.getById(teacher.getId()))
                    .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

            return Optional.of(teacherDAO.update(teacher))
                    .orElseThrow(() -> new RuntimeException("Runtime exception"));

        } catch (SpecialityDAOException e) {
            throw new RuntimeException(e);
        } catch (UserDAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTeacher(Integer id) throws TeacherNotFoundException, TeacherDAOException {
        if (id == null) return;

        try {
            if (teacherDAO.getById(id) == null) {
                throw new TeacherNotFoundException("Teacher not found");
            }
            teacherDAO.delete(id);
        } catch (TeacherDAOException |TeacherNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Teacher getTeacherById(Integer id) throws TeacherDAOException, TeacherNotFoundException {
        Teacher teacher;
        try {
            teacher = teacherDAO.getById(id);
            if (teacher == null) {
                throw new TeacherNotFoundException("Teacher with id: " + id + " not found");
            }
        } catch (TeacherDAOException | TeacherNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return teacher;
    }

    @Override
    public List<Teacher> getTeachersByLastname(String lastname) throws TeacherDAOException {
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = teacherDAO.getByLastname(lastname);
            return teachers;
        } catch (TeacherDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Teacher map(TeacherInsertDTO dto) throws SpecialityDAOException, UserDAOException {
            return new Teacher(null ,dto.getFirstname(), dto.getLastname(), dto.getSsn(), dto.getSpecialityId(), dto.getUsernameId());

    }

    private Teacher map(TeacherUpdateDTO dto) throws SpecialityDAOException, UserDAOException {
        return new Teacher(null ,dto.getFirstname(), dto.getLastname(), dto.getSsn(), dto.getSpecialityId(), dto.getUsernameId());
    }

//    private Integer specialityId(TeacherInsertDTO dto) throws SpecialityDAOException {
//        Integer speciality = dto.getSpecialityId();
//        int id = 0;
//        try {
//            id = specialityDAO.getByName(speciality).getId();
//        } catch (SpecialityDAOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return id;
//    }
//
//    private Integer specialityId(TeacherUpdateDTO dto) throws SpecialityDAOException {
//        String speciality = dto.getSpeciality();
//        int id = 0;
//        try {
//            id = specialityDAO.getByName(speciality).getId();
//        } catch (SpecialityDAOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return id;
//    }
//
//    private Integer userId(TeacherInsertDTO dto) throws UserDAOException {
//        String username = dto.getUsername();
//        List<User> users = userDAO.getByUsername(username);
//        try {
//            if (users.isEmpty()) {
//                return null;
//            }
//            return users.get(0).getId();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    private Integer userId(TeacherUpdateDTO dto) throws UserDAOException {
//        String username = dto.getUsername();
//        List<User> users = userDAO.getByUsername(username);
//        try {
//            if (users.isEmpty()) {
//                return null;
//            }
//            return users.get(0).getId();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
}
