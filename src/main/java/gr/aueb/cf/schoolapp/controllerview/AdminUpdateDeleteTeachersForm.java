package gr.aueb.cf.schoolapp.controllerview;



import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.dao.exceptions.*;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.*;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import gr.aueb.cf.schoolapp.service.util.DateUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUpdateDeleteTeachersForm extends JFrame {

    private static final long serialVersionUID = 1987L;
    private final JPanel contentPane;
    private final JTextField idTxt;
    private final JTextField firstnameTxt;
    private final JTextField lastnameTxt;
    private final JTextField ssnTxt;
    private final JComboBox<String> specialityComboBox = new JComboBox<String>();
    private final JComboBox<String> usernameComboBox = new JComboBox<String>();
    private Map<Integer, String> specialities;
    private Map<Integer, String> usernames;
    private DefaultComboBoxModel<String> specialitiesModel;
    private DefaultComboBoxModel<String> usernamesModel;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private final JButton firstBtn;
    private final JButton prevBtn;
    private final JButton nextBtn;
    private final JButton lastBtn;

    private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);
    private final ISpecialityDAO specialityDAO = new SpecialityDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();
    private List<Teacher> teachers;
    private int listPosition;
    private int listSize;
    public AdminUpdateDeleteTeachersForm() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("eduv2.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {

                try  {
                    teachers = teacherService.getTeachersByLastname(Main.getTeachersMenu().getLastname());
                    listPosition = 0;
                    listSize = teachers.size();

                    if (listSize == 0) {
                        JOptionPane.showMessageDialog(null, "No Teachers Found", "Info", JOptionPane.ERROR_MESSAGE);
                        Main.getTeachersMenu().setEnabled(true);
                        Main.getAdminUpdateDeleteTeachersForm().setVisible(false);
                        return;
                    }
                    idTxt.setText("");
                    firstnameTxt.setText("");
                    lastnameTxt.setText("");
                    ssnTxt.setText("");
                    specialityComboBox.setSelectedItem(null);
                    usernameComboBox.setSelectedItem(null);

                    PreparedStatement psSpecialities;
                    ResultSet rsSpecialities;
                    try(Connection conn = DBUtil.getConnection()) {

                        String sqlSpecialities = "SELECT * FROM Specialities";
                        psSpecialities = conn.prepareStatement(sqlSpecialities);
                        rsSpecialities = psSpecialities.executeQuery();
                        specialities = new HashMap<>();
                        specialitiesModel = new DefaultComboBoxModel<>();

                        while (rsSpecialities.next()) {
                            String speciality = rsSpecialities.getString("SPECIALITY");
                            int id = rsSpecialities.getInt("ID");
                            specialities.put(id, speciality);
                            specialitiesModel.addElement(speciality);
                        }
                        specialityComboBox.setModel(specialitiesModel);
                        specialityComboBox.setMaximumRowCount(5);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }


                    PreparedStatement psUsers;
                    ResultSet rsUsers;
                    try (Connection conn = DBUtil.getConnection()) {
                        String sqlUsers = "SELECT ID, USERNAME FROM USERS";
                        psUsers = conn.prepareStatement(sqlUsers);
                        rsUsers = psUsers.executeQuery(sqlUsers);
                        usernames = new HashMap<>();
                        usernamesModel = new DefaultComboBoxModel<>();

                        while (rsUsers.next()) {
                            String username = rsUsers.getString("USERNAME");
                            int id = rsUsers.getInt("ID");
                            usernames.put(id, username);
                            usernamesModel.addElement(username);
                        }
                        usernameComboBox.setModel(usernamesModel);
                        usernameComboBox.setMaximumRowCount(5);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    idTxt.setText(Integer.toString(teachers.get(listPosition).getId()));
                    firstnameTxt.setText(teachers.get(listPosition).getFirstname());
                    lastnameTxt.setText(teachers.get(listPosition).getLastname());
                    ssnTxt.setText(teachers.get(listPosition).getSsn().toString());
                    specialityComboBox.setSelectedItem(specialities.get(teachers.get(listPosition).getSpecialityId()));
                    usernameComboBox.setSelectedItem(usernames.get(teachers.get(listPosition).getUserId()));
                } catch (TeacherDAOException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog(null, message, "Error in getting studentr", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setTitle("ενημέρωση/ Διαγραφή Εκπαιδευτών");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 503, 507);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel contentPane_1 = new JPanel();
        contentPane_1.setLayout(null);
        contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane_1.setBounds(27, 10, 474, 548);
        contentPane.add(contentPane_1);

        JLabel idLbl = new JLabel("ID");
        idLbl.setForeground(new Color(128, 0, 0));
        idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        idLbl.setBounds(92, 15, 22, 34);
        contentPane_1.add(idLbl);

        idTxt = new JTextField();
        idTxt.setEditable(false);
        idTxt.setColumns(10);
        idTxt.setBackground(new Color(252, 252, 205));
        idTxt.setBounds(124, 25, 59, 20);
        contentPane_1.add(idTxt);

        JLabel firstnameLbl = new JLabel("Όνομα");
        firstnameLbl.setForeground(new Color(128, 0, 0));
        firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        firstnameLbl.setBounds(60, 71, 49, 17);
        contentPane_1.add(firstnameLbl);

        firstnameTxt = new JTextField();
        firstnameTxt.setColumns(10);
        firstnameTxt.setBounds(124, 69, 203, 19);
        contentPane_1.add(firstnameTxt);

        JLabel lastnameLbl = new JLabel("Επώνυμο");
        lastnameLbl.setForeground(new Color(128, 0, 0));
        lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        lastnameLbl.setBounds(33, 151, 76, 14);
        contentPane_1.add(lastnameLbl);

        lastnameTxt = new JTextField();
        lastnameTxt.setColumns(10);
        lastnameTxt.setBounds(124, 148, 203, 20);
        contentPane_1.add(lastnameTxt);

        JLabel specialityLbl = new JLabel("Ειδικότητα");
        specialityLbl.setForeground(new Color(128, 0, 0));
        specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        specialityLbl.setBounds(33, 187, 76, 14);
        contentPane_1.add(specialityLbl);
        specialityComboBox.setBounds(124, 185, 173, 22);
        contentPane_1.add(specialityComboBox);

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(new Color(128, 0, 0));
        usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        usernameLbl.setBounds(33, 229, 76, 14);
        contentPane_1.add(usernameLbl);
        usernameComboBox.setBounds(124, 225, 173, 22);
        contentPane_1.add(usernameComboBox);

        firstBtn = new JButton("");
        URL firstBtnUrl = classLoader.getResource("First record.png");
        ImageIcon icon = new ImageIcon(firstBtnUrl);
        firstBtn.setIcon(icon);
        firstBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listSize > 0) {
                    listPosition = 0;

                    idTxt.setText(String.format("%s", teachers.get(listPosition).getId()));
                    firstnameTxt.setText(teachers.get(listPosition).getFirstname());
                    lastnameTxt.setText(teachers.get(listPosition).getLastname());
                    ssnTxt.setText(teachers.get(listPosition).getSsn().toString());
                    specialityComboBox.setSelectedItem(specialities.get(teachers.get(listPosition).getSpecialityId()));
                    usernameComboBox.setSelectedItem(usernames.get(teachers.get(listPosition).getUserId()));
                }
            }
        });
        firstBtn.setBounds(73, 302, 49, 40);
        contentPane_1.add(firstBtn);

        prevBtn = new JButton("");
        URL prevBtnUrl = classLoader.getResource("Previous_record.png");
        ImageIcon icon2 = new ImageIcon(prevBtnUrl);
        prevBtn.setIcon(icon2);
        prevBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listPosition > 0) {
                    listPosition--;
                    idTxt.setText(String.format("%s", teachers.get(listPosition).getId()));
                    firstnameTxt.setText(teachers.get(listPosition).getFirstname());
                    lastnameTxt.setText(teachers.get(listPosition).getLastname());
                    ssnTxt.setText(teachers.get(listPosition).getSsn().toString());
                    specialityComboBox.setSelectedItem(specialities.get(teachers.get(listPosition).getSpecialityId()));
                    usernameComboBox.setSelectedItem(usernames.get(teachers.get(listPosition).getUserId()));
                }
            }
        });
        prevBtn.setBounds(120, 302, 49, 40);
        contentPane_1.add(prevBtn);

        nextBtn = new JButton("");
        URL nextBtnUrl = classLoader.getResource("Next_track.png");
        ImageIcon iconnext = new ImageIcon(nextBtnUrl);
        nextBtn.setIcon(iconnext);
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listPosition <= listSize - 2) {
                    listPosition++;
                    idTxt.setText(String.format("%s", teachers.get(listPosition).getId()));
                    firstnameTxt.setText(teachers.get(listPosition).getFirstname());
                    lastnameTxt.setText(teachers.get(listPosition).getLastname());
                    ssnTxt.setText(teachers.get(listPosition).getSsn().toString());
                    specialityComboBox.setSelectedItem(specialities.get(teachers.get(listPosition).getSpecialityId()));
                    usernameComboBox.setSelectedItem(usernames.get(teachers.get(listPosition).getUserId()));
                }
            }
        });
        nextBtn.setBounds(165, 302, 49, 40);
        contentPane_1.add(nextBtn);

        lastBtn = new JButton("");
        URL lastBtnUrl = classLoader.getResource("Last_Record.png");
        ImageIcon iconlast = new ImageIcon(lastBtnUrl);
        lastBtn.setIcon(iconlast);
        lastBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listSize > 0) {
                    listPosition = listSize - 1;
                    idTxt.setText(String.format("%s", teachers.get(listPosition).getId()));
                    firstnameTxt.setText(teachers.get(listPosition).getFirstname());
                    lastnameTxt.setText(teachers.get(listPosition).getLastname());
                    ssnTxt.setText(teachers.get(listPosition).getSsn().toString());
                    specialityComboBox.setSelectedItem(specialities.get(teachers.get(listPosition).getSpecialityId()));
                    usernameComboBox.setSelectedItem(usernames.get(teachers.get(listPosition).getUserId()));
                }
            }
        });
        lastBtn.setBounds(213, 302, 49, 40);
        contentPane_1.add(lastBtn);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 276, 303, 2);
        contentPane_1.add(separator);

        JButton deleteBtn = new JButton("Διαγραφή");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int response;
                    int id;

                    String idStr = idTxt.getText();
                    id = Integer.parseInt(idTxt.getText());

                    if (idStr.equals("")) return;

                    response = JOptionPane.showConfirmDialog (null, "Είστε σίγουρος;",
                            "Warning", JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION){
                        teacherService.deleteTeacher(id);
                        JOptionPane.showMessageDialog (null, "Teacher was deleted successfully",
                                "DELETE", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (TeacherDAOException | TeacherNotFoundException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog (null, message, "DELETE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteBtn.setForeground(Color.BLUE);
        deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        deleteBtn.setBounds(157, 352, 108, 58);
        contentPane_1.add(deleteBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getAdminUpdateDeleteTeachersForm().setVisible(false);
                Main.getTeachersMenu().setEnabled(true);
                Main.getTeachersMenu().setVisible(true);
            }
        });
        closeBtn.setForeground(Color.BLUE);
        closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        closeBtn.setBounds(302, 392, 108, 58);
        contentPane_1.add(closeBtn);

        JButton updateBtn = new JButton("Ενημέρωση");
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ssnTxt == null|| specialityComboBox.getSelectedItem() == null
                        || usernameComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please select ssn / speciality / username", "ssn", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String id = idTxt.getText().trim();
                String firstname = firstnameTxt.getText().trim();
                String lastname = lastnameTxt.getText().trim();
                Integer ssn = Integer.parseInt(ssnTxt.getText().trim());
                String speciality =  (String) specialityComboBox.getSelectedItem();
                String username = (String) usernameComboBox.getSelectedItem();

                if (lastname.equals("") || firstname.equals("") || id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Not valid input", "UPDATE ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Speciality speciality1 = specialityDAO.getByName(speciality);
                    User user = userDAO.getByUsername(username).get(0);
                    Integer specialityId = speciality1.getId();
                    Integer usernameId = user.getId();


                    TeacherUpdateDTO dto = new TeacherUpdateDTO();
                    dto.setId(Integer.parseInt(id));
                    dto.setFirstname(firstname);
                    dto.setLastname(lastname);
                    dto.setSsn(ssn);
                    dto.setSpecialityId(specialityId);
                    dto.setUsernameId(usernameId);
                    Teacher teacher = teacherService.updateTeacher(dto);
                    JOptionPane.showMessageDialog(null, "Student "
                            + " was updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
                } catch (TeacherDAOException | TeacherNotFoundException ex) {
                    String message = ex.getMessage();
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SpecialityDAOException | UserDAOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateBtn.setForeground(Color.BLUE);
        updateBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        updateBtn.setBounds(46, 352, 108, 58);
        contentPane_1.add(updateBtn);

        ssnTxt = new JTextField();
        ssnTxt.setColumns(10);
        ssnTxt.setBounds(124, 110, 203, 20);
        contentPane_1.add(ssnTxt);

        JLabel ssnLbl = new JLabel("Αρ. Μητρώου");
        ssnLbl.setForeground(new Color(128, 0, 0));
        ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        ssnLbl.setBounds(10, 113, 99, 14);
        contentPane_1.add(ssnLbl);
    }
}
