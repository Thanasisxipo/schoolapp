package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.dao.exceptions.*;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.service.util.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements IStudentService {
    private final IStudentDAO studentDAO;


    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    @Override
    public Student insertStudent(StudentInsertDTO dto) throws StudentDAOException {
        if (dto == null) return null;

        try {
            return Optional.ofNullable(studentDAO.insert(map(dto)))
                    .orElseThrow(() -> new RuntimeException(""));
        } catch (StudentDAOException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException | CityDAOException | UserDAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws StudentDAOException {
        if (dto == null) return null;
        Student student = null;
        try {
            student = map(dto);
            Student existingStudent = Optional.of(studentDAO.getById(student.getId()))
                    .orElseThrow(() -> new StudentNotFoundException("Student not found"));

            return Optional.ofNullable(studentDAO.update(student))
                    .orElseThrow(() -> new RuntimeException("Runtime exception"));
        } catch (UserDAOException | CityDAOException | ParseException |
                 StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(Integer id) throws StudentNotFoundException, StudentDAOException {
        if (id == null) return;

        try {
            if (studentDAO.getById(id) == null) {
                throw new StudentNotFoundException("Teacher not found");
            }
            studentDAO.delete(id);
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Student getStudentById(Integer id) throws StudentNotFoundException, StudentDAOException {
        Student student;
        try {
            student = studentDAO.getById(id);
            if (student == null) {
                throw new StudentNotFoundException("Student with id: " + id + " not found");
            }
        } catch (StudentNotFoundException | StudentDAOException e) {
            e.printStackTrace();
            throw e;

        }
        return student;
    }

    @Override
    public List<Student> getStudentByLastname(String lastname) throws StudentDAOException {
        List<Student> students = new ArrayList<>();
        try {
            students = studentDAO.getByLastName(lastname);
            return students;
        } catch (StudentDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student map(StudentInsertDTO dto) throws UserDAOException, CityDAOException, ParseException {
        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getGender(), DateUtil.toDate(dto.getBirthDate()), dto.getCityId(), dto.getUsernameId());

    }

    private Student map(StudentUpdateDTO dto) throws UserDAOException, CityDAOException, ParseException {
        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getGender(), DateUtil.toDate(dto.getBirthDate()), dto.getCityId(), dto.getUsernameId());
    }


//    private Integer cityId(StudentInsertDTO dto) throws CityDAOException {
//        Integer city = dto.getCity();
//        int id = 0;
//        try {
//            id = cityDAO.getByName(city).getId();
//        } catch (CityDAOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return id;
//    }
//
//    private Integer cityId(StudentUpdateDTO dto) throws CityDAOException {
//        String city = dto.getCity();
//        int id = 0;
//        try {
//            id = cityDAO.getByName(city).getId();
//        } catch (CityDAOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return id;
//    }
//
//    private Integer userId(StudentInsertDTO dto) throws UserDAOException {
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
//    private Integer userId(StudentUpdateDTO dto) throws UserDAOException {
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
