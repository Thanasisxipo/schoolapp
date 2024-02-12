package gr.aueb.cf.schoolapp;

import gr.aueb.cf.schoolapp.controllerview.*;

import java.awt.*;

public class Main {

    private static Login loginForm;
    private static AdminMenu adminMenu;
    private static TeachersMenu teachersMenu;
    private static AdminInsertStudentsForm adminInsertStudentsForm;
    private static AdminInsertTeachersForm adminInsertTeacehrsForm;
    private static AdminUpdateDeleteStudentsForm adminUpdateDeleteStudentsForm;
    private static AdminUpdateDeleteTeachersForm adminUpdateDeleteTeachersForm;
    private static StudentsMenu studentsMenu;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    loginForm = new Login();
                    loginForm.setLocationRelativeTo(null);
                    loginForm.setVisible(true);

                    adminMenu = new AdminMenu();
                    adminMenu.setLocationRelativeTo(null);
                    adminMenu.setVisible(false);

                    teachersMenu = new TeachersMenu();
                    teachersMenu.setLocationRelativeTo(null);
                    teachersMenu.setVisible(false);

                    studentsMenu = new StudentsMenu();
                    studentsMenu.setLocationRelativeTo(null);
                    studentsMenu.setVisible(false);

                    adminInsertTeacehrsForm = new AdminInsertTeachersForm();
                    adminInsertTeacehrsForm.setLocationRelativeTo(null);
                    adminInsertTeacehrsForm.setVisible(false);

                    adminInsertStudentsForm = new AdminInsertStudentsForm();
                    adminInsertStudentsForm.setLocationRelativeTo(null);
                    adminInsertStudentsForm.setVisible(false);

                    adminUpdateDeleteStudentsForm = new AdminUpdateDeleteStudentsForm();
                    adminUpdateDeleteStudentsForm.setLocationRelativeTo(null);
                    adminUpdateDeleteStudentsForm.setVisible(false);

                    adminUpdateDeleteTeachersForm = new AdminUpdateDeleteTeachersForm();
                    adminUpdateDeleteTeachersForm.setLocationRelativeTo(null);
                    adminUpdateDeleteTeachersForm.setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Login getLoginForm() {
        return loginForm;
    }

    public static AdminMenu getAdminMenu() {
        return adminMenu;
    }

    public static TeachersMenu getTeachersMenu() {
        return teachersMenu;
    }

    public static AdminInsertStudentsForm getAdminInsertStudentsForm() {
        return adminInsertStudentsForm;
    }

    public static StudentsMenu getStudentsMenu() {
        return studentsMenu;
    }

    public static AdminInsertTeachersForm getAdminInsertTeacehrsForm() {
        return adminInsertTeacehrsForm;
    }

    public static AdminUpdateDeleteStudentsForm getAdminUpdateDeleteStudentsForm() {
        return adminUpdateDeleteStudentsForm;
    }
    public static AdminUpdateDeleteTeachersForm getAdminUpdateDeleteTeachersForm() {
        return adminUpdateDeleteTeachersForm;
    }
}
