package gr.aueb.cf.schoolapp.controllerview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import javax.swing.JButton;
import java.awt.Toolkit;

public class AdminInsertTeachersForm extends JFrame {

	private static final long serialVersionUID = 123457;

	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JLabel lastnameLbl;
	private JTextField lastnameTxt;
	private JLabel ssnLbl;
	private JTextField ssnTxt;
	private JLabel specialityLbl;
	private JComboBox<String> specialityComboBox;
	private JComboBox<String> usernameComboBox;
	private Map<String, Integer> specialities;
	private Map<String, Integer> usernames;
	private DefaultComboBoxModel<String> model;

	public AdminInsertTeachersForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				firstnameTxt.setText("");
				lastnameTxt.setText("");
				ssnTxt.setText("");
				specialityComboBox.setSelectedItem(null);
				usernameComboBox.setSelectedItem(null);
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 359);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Όνομα");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(68, 75, 56, 17);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(129, 73, 207, 20);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		lastnameLbl = new JLabel("Επώνυμο");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(52, 135, 72, 17);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(129, 133, 207, 20);
		contentPane.add(lastnameTxt);
		
		ssnLbl = new JLabel("Αρ. Μητρώου");
		ssnLbl.setForeground(new Color(128, 0, 0));
		ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		ssnLbl.setBounds(25, 105, 99, 17);
		contentPane.add(ssnLbl);
		
		ssnTxt = new JTextField();
		ssnTxt.setColumns(10);
		ssnTxt.setBounds(129, 103, 207, 20);
		contentPane.add(ssnTxt);
	
		
		specialityLbl = new JLabel("Ειδικότητα");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(39, 162, 76, 17);
		contentPane.add(specialityLbl);
		
		specialityComboBox = new JComboBox<>();
		
		specialityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String sql = "SELECT * FROM SPECIALITIES";
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	specialities = new HashMap<>();
			    	model = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String speciality = rs.getString("CITY");
			    		int id = rs.getInt("ID");
			    		specialities.put(speciality, id);
			    		model.addElement(speciality);
			    	}
			    	specialityComboBox.setModel(model);
			    	specialityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		specialityComboBox.setBounds(129, 162, 207, 20);
		contentPane.add(specialityComboBox);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(49, 189, 81, 17);
		contentPane.add(usernameLbl);

		usernameComboBox = new JComboBox<>();
		usernameComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String sql = "SELECT * FROM USERNAMES";
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	usernames = new HashMap<>();
			    	model = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String username = rs.getString("CITY");
			    		int id = rs.getInt("ID");
			    		usernames.put(username, id);
			    		model.addElement(username);
			    	}
			    	usernameComboBox.setModel(model);
			    	usernameComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		usernameComboBox.setBounds(130, 189, 207, 20);
		contentPane.add(usernameComboBox);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeacherInsertDTO dto;
				if (ssnTxt.getText() == "" || specialityComboBox.getSelectedItem() == null
						|| usernameComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please insert ssn / speciality / username", "SPECIALITY", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {

					String firstname = firstnameTxt.getText().trim();
					String lastname = lastnameTxt.getText().trim();
					String ssn = ssnTxt.getText();
					String speciality = (String) specialityComboBox.getSelectedItem();
					String username = (String) usernameComboBox.getSelectedItem();
					Integer specialiyId = specialities.get(speciality);
					Integer usernameId = usernames.get(username);

					if (firstname == "" || lastname == "" | ssn == "") {
						JOptionPane.showMessageDialog(null, "Please fill firstname / lastname / ssn", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}

					dto = new TeacherInsertDTO();
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setSsn(ssn);
					dto.setSpecialityId(specialiyId);
					dto.setUsernameId(usernameId);

					Teacher teacher = teacherService.insertTeacher(dto);
					JOptionPane.showMessageDialog(null, "Teacher" + teacher.getLastname()
							+ " was inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
				} catch (TeacherDAOException ex) {
                    throw new RuntimeException(ex);
                }

            }
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(229, 239, 108, 58);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminInsertTeacehrsForm().setVisible(false);
				Main.getTeachersMenu().setEnabled(true);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(366, 239, 108, 58);
		contentPane.add(closeBtn);
		
	}
}
