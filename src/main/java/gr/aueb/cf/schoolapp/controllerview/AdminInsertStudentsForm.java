package gr.aueb.cf.schoolapp.controllerview;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class AdminInsertStudentsForm extends JFrame {
	
	
	private static final long serialVersionUID = 123456;

	private final IStudentDAO studentDAO = new StudentDAOImpl();
	private final IStudentService studentService = new StudentServiceImpl(studentDAO);

	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JLabel lastnameLbl;
	private JTextField lastnameTxt;
	private JLabel genderLbl;
	private JLabel cityLbl;
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private Map<String, Integer> cities;
	private Map<String, Integer> usernames;
	private DefaultComboBoxModel<String> citiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private JTextField birthDateTxt;
	private JLabel usernameLbl;
	private ButtonGroup buttonGroup = new ButtonGroup(); 
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;

	public AdminInsertStudentsForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				
 				firstnameTxt.setText("");
				lastnameTxt.setText("");
				maleRdBtn.setSelected(true);
				birthDateTxt.setText("");
				cityComboBox.setSelectedItem(null);
				usernameComboBox.setSelectedItem(null);	
			}
		});
		setTitle("Εισαγωγή Εκπαιδευόμενου");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 354);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Όνομα");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(80, 44, 56, 17);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(141, 42, 207, 20);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		lastnameLbl = new JLabel("Επώνυμο");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(64, 76, 72, 17);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(141, 74, 207, 20);
		contentPane.add(lastnameTxt);
		
		genderLbl = new JLabel("Φύλο");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(92, 107, 44, 17);
		contentPane.add(genderLbl);
		
		// buttonGroup = new ButtonGroup();
		maleRdBtn = new JRadioButton("Άνδρας");
		maleRdBtn.setBounds(141, 104, 81, 23);
		contentPane.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Γυναίκα");
		femaleRdBtn.setBounds(224, 104, 95, 23);
		contentPane.add(femaleRdBtn);
		
		maleRdBtn.setActionCommand("M");
        femaleRdBtn.setActionCommand("F");
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(92, 169, 44, 17);
		contentPane.add(cityLbl);

		
		cityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT * FROM CITIES";
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	cities = new HashMap<>();
			    	citiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String city = rs.getString("CITY");
			    		int id = rs.getInt("ID");
			    		cities.put(city, id);
			    		citiesModel.addElement(city);
			    	}
			    	cityComboBox.setModel(citiesModel);
			    	cityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		cityComboBox.setBounds(141, 167, 207, 20);
		contentPane.add(cityComboBox);
		
		JLabel birthDateLbl = new JLabel("Ημ. Γέννησης");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(37, 138, 99, 17);
		contentPane.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setColumns(10);
		birthDateTxt.setBounds(141, 136, 207, 20);
		contentPane.add(birthDateTxt);
		
		usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(60, 198, 81, 17);
		contentPane.add(usernameLbl);
		
		usernameComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT ID, USERNAME FROM USERS";
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery(sql)) {
			    	usernames = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String username = rs.getString("USERNAME");
			    		int id = rs.getInt("ID");
			    		usernames.put(username, id);
			    		usernamesModel.addElement(username);
			    	}
			    	usernameComboBox.setModel(usernamesModel);
			    	usernameComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		usernameComboBox.setBounds(141, 198, 207, 20);
		contentPane.add(usernameComboBox);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e) {
//				Map<String, String> studentErrors = new HashMap<>();
				StudentInsertDTO dto;
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
						|| usernameComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender / city / username", "Gender", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					String firstname = firstnameTxt.getText().trim();
					String lastname = lastnameTxt.getText().trim();
					String gender = buttonGroup.getSelection().getActionCommand();
					String birthDate = birthDateTxt.getText().trim();
					String city = (String) cityComboBox.getSelectedItem();
					String username = (String) usernameComboBox.getSelectedItem();
					Integer cityId = cities.get(city);
					Integer usernameId = usernames.get(username);

					if (firstname == "" || lastname == "" || birthDate == "") {
						JOptionPane.showMessageDialog(null, "Please fill firstname / lastname / birthdate", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}

					dto = new StudentInsertDTO();
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setBirthDate(birthDate);
					dto.setGender(gender);
					dto.setCityId(cityId);
					dto.setUsernameId(usernameId);


					Student student = studentService.insertStudent(dto);
					JOptionPane.showMessageDialog(null, "Student" + student.getLastname()
							+ " was inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
				} catch (StudentDAOException e1) {
					String message = e1.getMessage();
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
				}


			}
		});
		insertBtn.setForeground(new Color(0, 0, 255));
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(240, 248, 108, 58);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setEnabled(true);
				Main.getAdminInsertStudentsForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(377, 248, 108, 58);
		contentPane.add(closeBtn);
		
	}
}
