package gr.aueb.cf.schoolapp.controllerview;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import gr.aueb.cf.schoolapp.service.util.DateUtil;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUpdateDeleteTeachersForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField ssnTxt;

	private JComboBox<String> specialityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private Map<Integer, String> specialities;
	private Map<Integer, String> usernames;
	private DefaultComboBoxModel<String> specialitiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private JButton firstBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton lastBtn;

	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);
	private int listPosition;
	private int listSize;
	private List<Teacher> teachers;

	public AdminUpdateDeleteTeachersForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					teachers = teacherService.getTeachersByLastname(Main.getTeachersMenu().getLastname());
					listPosition = 0;
					listSize = teachers.size();

					if (listSize == 0) {
						firstnameTxt.setText("");
						lastnameTxt.setText("");
						ssnTxt.setText("");
						specialityComboBox.setSelectedItem(null);
						usernameComboBox.setSelectedItem(null);
					}

				} catch (TeacherDAOException e1) {
					String message = e1.getMessage();
					JOptionPane.showMessageDialog(null, message, "Error in getting studentr", JOptionPane.ERROR_MESSAGE);
				}

				PreparedStatement psSpecialities;
				ResultSet rsSpecialities;
				try (Connection conn = DBUtil.getConnection()) {

					String sqlSpecialities = "SELECT * FROM SPECIALITIES";
					psSpecialities = conn.prepareStatement(sqlSpecialities);
					rsSpecialities = psSpecialities.executeQuery();
					specialities = new HashMap<>();
					specialitiesModel = new DefaultComboBoxModel<>();

					while (rsSpecialities.next()) {
						String city = rsSpecialities.getString("SPECIALITY");
						int id = rsSpecialities.getInt("ID");
						specialities.put(id, city);
						specialitiesModel.addElement(city);
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

				try {
					idTxt.setText(Integer.toString(rs.getInt("ID")));
					firstnameTxt.setText(rs.getString("FIRSTNAME"));
					lastnameTxt.setText(rs.getString("LASTNAME"));
					ssnTxt.setText("SSN");
					specialityComboBox.setSelectedItem(specialities.get(rs.getInt("SPECIALITY_ID")));
					usernameComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
				} catch (SQLException e1) {
					e1.printStackTrace();

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
		JComboBox<String> specialityComboBox = new JComboBox<String>();
		specialityComboBox.setBounds(124, 185, 173, 22);
		contentPane_1.add(specialityComboBox);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(33, 229, 76, 14);
		contentPane_1.add(usernameLbl);
		JComboBox<String> usernameComboBox = new JComboBox<String>();
		usernameComboBox.setBounds(124, 225, 173, 22);
		contentPane_1.add(usernameComboBox);
		
		JButton firstBtn = new JButton("");
		URL firstBtnUrl = classLoader.getResource("First record.png");
		ImageIcon icon = new ImageIcon(firstBtnUrl);
		firstBtn.setIcon(icon);
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.first()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						ssnTxt.setText(rs.getString("SSN"));
						specialityComboBox.setSelectedItem(specialities.get(rs.getInt("SPECIALITY_ID")));
						usernameComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setBounds(73, 302, 49, 40);
		contentPane_1.add(firstBtn);
		
		JButton prevBtn = new JButton("");
		URL prevBtnUrl = classLoader.getResource("Previous_record.png");
		ImageIcon icon2 = new ImageIcon(prevBtnUrl);
		prevBtn.setIcon(icon2);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.previous()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						ssnTxt.setText(rs.getString("SSN"));
						specialityComboBox.setSelectedItem(specialities.get(rs.getInt("SPECIALITY_ID")));
						usernameComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					} else {
						rs.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		prevBtn.setBounds(120, 302, 49, 40);
		contentPane_1.add(prevBtn);
		
		JButton nextBtn = new JButton("");
		URL nextBtnUrl = classLoader.getResource("Next_track.png");
		ImageIcon iconnext = new ImageIcon(nextBtnUrl);
		nextBtn.setIcon(iconnext);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						ssnTxt.setText(rs.getString("SSN"));
						specialityComboBox.setSelectedItem(specialities.get(rs.getInt("SPECIALITY_ID")));
						usernameComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					} else {
						rs.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		nextBtn.setBounds(165, 302, 49, 40);
		contentPane_1.add(nextBtn);
		
		JButton lastBtn = new JButton("");
		URL lastBtnUrl = classLoader.getResource("Last_Record.png");
		ImageIcon iconlast = new ImageIcon(lastBtnUrl);
		lastBtn.setIcon(iconlast);
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.last()) {
						idTxt.setText(Integer.toString(rs.getInt("ID")));
						firstnameTxt.setText(rs.getString("FIRSTNAME"));
						lastnameTxt.setText(rs.getString("LASTNAME"));
						ssnTxt.setText(rs.getString("SSN"));
						specialityComboBox.setSelectedItem(specialities.get(rs.getInt("SPECIALITY_ID")));
						usernameComboBox.setSelectedItem(usernames.get(rs.getInt("USER_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
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
				Main.getAdminUpdateDeleteStudentsForm().setVisible(false);
				Main.getTeachersMenu().setEnabled(true);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(302, 392, 108, 58);
		contentPane_1.add(closeBtn);
		
		JButton updateBtn = new JButton("Ενημέρωση");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idTxt.getText();
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String ssn = ssnTxt.getText().trim();
				int specialityId = (Integer) specialityComboBox.getSelectedItem();
				int usernameId = (Integer) usernameComboBox.getSelectedItem();

				if (lastname.equals("") || firstname.equals("") || id.equals("")) {
					JOptionPane.showMessageDialog(null, "Not valid input", "UPDATE ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					TeacherUpdateDTO dto = new TeacherUpdateDTO();
					dto.setId(Integer.parseInt(id));
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setSsn(ssn);
					dto.setSpecialityId(specialityId);
					dto.setUsernameId(usernameId);
					Teacher teacher =teacherService.updateTeacher(dto);
					JOptionPane.showMessageDialog(null, "Teacher "
							+ " was updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
				} catch (TeacherDAOException | TeacherNotFoundException ex) {
					String message = ex.getMessage();
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
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
