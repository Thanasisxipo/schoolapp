package gr.aueb.cf.schoolapp.controllerview;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.service.util.DateUtil;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminUpdateDeleteStudentsForm extends JFrame {
	private static final long serialVersionUID = 123459;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField birthDateTxt;
	
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private Map<Integer, String> cities;
	private Map<Integer, String> usernames;
	private DefaultComboBoxModel<String> citiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;
	private JButton firstBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton lastBtn;

	private final IStudentDAO studentDAO = new StudentDAOImpl();
	private final IStudentService studentService = new StudentServiceImpl(studentDAO);
	private int listPosition;
	private int listSize;
	private List<Student> students;

	public AdminUpdateDeleteStudentsForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				try  {
						students = studentService.getStudentByLastname(Main.getStudentsMenu().getLastname());
						listPosition = 0;
						listSize = students.size();

						if (listSize == 0) {
							idTxt.setText("");
							firstnameTxt.setText("");
							lastnameTxt.setText("");
							maleRdBtn.setSelected(true);
							birthDateTxt.setText("");
							cityComboBox.setSelectedItem(null);
							usernameComboBox.setSelectedItem(null);
						}
						PreparedStatement psCities;
						ResultSet rsCities;
						try(Connection conn = DBUtil.getConnection()) {

						String sqlCities = "SELECT * FROM CITIES";
						psCities = conn.prepareStatement(sqlCities);
						rsCities = psCities.executeQuery();
						cities = new HashMap<>();
						citiesModel = new DefaultComboBoxModel<>();

						while (rsCities.next()) {
							String city = rsCities.getString("CITY");
							int id = rsCities.getInt("ID");
							cities.put(id, city);
							citiesModel.addElement(city);
						}
						cityComboBox.setModel(citiesModel);
						cityComboBox.setMaximumRowCount(5);

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

								idTxt.setText(Integer.toString(students.get(listPosition).getId()));
							firstnameTxt.setText(students.get(listPosition).getFirstname());
							lastnameTxt.setText(students.get(listPosition).getLastname());
							if (students.get(listPosition).getGender().equals("M")) {
								maleRdBtn.setSelected(true);
							} else {
								femaleRdBtn.setSelected(true);
							}
							birthDateTxt.setText(DateUtil.toString(students.get(listPosition).getBirthDate()));
							cityComboBox.setSelectedItem(cities.get(students.get(listPosition).getCityId()));
							usernameComboBox.setSelectedItem(usernames.get(students.get(listPosition).getUserId()));
				} catch (StudentDAOException e1) {
					String message = e1.getMessage();
					JOptionPane.showMessageDialog(null, message, "Error in getting studentr", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		setTitle("Ενημέρωση / Διαγραφή");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 585);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setForeground(new Color(128, 0, 0));
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl.setBounds(92, 27, 17, 17);
		contentPane.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setBackground(new Color(252, 252, 205));
		idTxt.setEditable(false);
		idTxt.setBounds(124, 25, 59, 20);
		contentPane.add(idTxt);
		idTxt.setColumns(10);
		
		JLabel firstnameLbl = new JLabel("Όνομα");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(60, 71, 49, 17);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(124, 69, 203, 19);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Επώνυμο");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(45, 115, 64, 14);
		contentPane.add(lblNewLabel);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setBounds(124, 112, 203, 20);
		contentPane.add(lastnameTxt);
		lastnameTxt.setColumns(10);
		
		JLabel genderLbl = new JLabel("Φύλο");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(72, 156, 37, 14);
		contentPane.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Άνδρας");
		maleRdBtn.setBounds(124, 152, 71, 23);
		contentPane.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Γυναίκα");
		femaleRdBtn.setBounds(208, 154, 71, 23);
		contentPane.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel birthDateLbl = new JLabel("Ημ. Γέννησης");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(10, 197, 99, 14);
		contentPane.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setBounds(124, 194, 133, 20);
		contentPane.add(birthDateTxt);
		birthDateTxt.setColumns(10);
		
		JLabel cityLbl = new JLabel("Πόλη");
		cityLbl.setForeground(new Color(128, 0, 0));
		cityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		cityLbl.setBounds(67, 238, 42, 14);
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
						cities.put(id, city);
						citiesModel.addElement(city);
					}
					cityComboBox.setModel(citiesModel);
					cityComboBox.setMaximumRowCount(5);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		cityComboBox.setBounds(124, 235, 173, 22);
		contentPane.add(cityComboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(38, 279, 71, 14);
		contentPane.add(lblNewLabel_1);

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
						usernames.put(id, username);
						usernamesModel.addElement(username);
					}
					usernameComboBox.setModel(usernamesModel);
					usernameComboBox.setMaximumRowCount(5);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		usernameComboBox.setBounds(124, 275, 173, 22);
		contentPane.add(usernameComboBox);
		
		firstBtn = new JButton("");
		URL firstBtnUrl = classLoader.getResource("First record.png");
		ImageIcon icon = new ImageIcon(firstBtnUrl);
		firstBtn.setIcon(icon);
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = 0;

					idTxt.setText(String.format("%s", students.get(listPosition).getId()));
					firstnameTxt.setText(students.get(listPosition).getFirstname());
					lastnameTxt.setText(students.get(listPosition).getLastname());
					if (students.get(listPosition).getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toString(students.get(listPosition).getBirthDate()));
					cityComboBox.setSelectedItem(cities.get(students.get(listPosition).getCityId()));
					usernameComboBox.setSelectedItem(usernames.get(students.get(listPosition).getUserId()));
				}
			}
		});
		
		firstBtn.setBounds(87, 350, 49, 40);
		contentPane.add(firstBtn);
		
		prevBtn = new JButton("");
		URL prevBtnUrl = classLoader.getResource("Previous_record.png");
		ImageIcon icon2 = new ImageIcon(prevBtnUrl);
		prevBtn.setIcon(icon2);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPosition > 0) {
					listPosition--;
					idTxt.setText(String.format("%s", students.get(listPosition).getId()));
					firstnameTxt.setText(students.get(listPosition).getFirstname());
					lastnameTxt.setText(students.get(listPosition).getLastname());
					if (students.get(listPosition).getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toString(students.get(listPosition).getBirthDate()));
					cityComboBox.setSelectedItem(cities.get(students.get(listPosition).getCityId()));
					usernameComboBox.setSelectedItem(usernames.get(students.get(listPosition).getUserId()));
				}
			}
		});
		
		prevBtn.setBounds(134, 350, 49, 40);
		contentPane.add(prevBtn);
		
		nextBtn = new JButton("");
		URL nextBtnUrl = classLoader.getResource("Next_track.png");
		ImageIcon iconnext = new ImageIcon(nextBtnUrl);
		nextBtn.setIcon(iconnext);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPosition <= listSize - 2) {
					listPosition++;
					idTxt.setText(String.format("%s", students.get(listPosition).getId()));
					firstnameTxt.setText(students.get(listPosition).getFirstname());
					lastnameTxt.setText(students.get(listPosition).getLastname());
					if (students.get(listPosition).getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toString(students.get(listPosition).getBirthDate()));
					cityComboBox.setSelectedItem(cities.get(students.get(listPosition).getCityId()));
					usernameComboBox.setSelectedItem(usernames.get(students.get(listPosition).getUserId()));
				}
			}
		});
		
		nextBtn.setBounds(179, 350, 49, 40);
		contentPane.add(nextBtn);
		
		lastBtn = new JButton("");
		URL lastBtnUrl = classLoader.getResource("Last_Record.png");
		ImageIcon iconlast = new ImageIcon(lastBtnUrl);
		lastBtn.setIcon(iconlast);
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = listSize - 1;
					idTxt.setText(String.format("%s", students.get(listPosition).getId()));
					firstnameTxt.setText(students.get(listPosition).getFirstname());
					lastnameTxt.setText(students.get(listPosition).getLastname());
					if (students.get(listPosition).getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toString(students.get(listPosition).getBirthDate()));
					cityComboBox.setSelectedItem(cities.get(students.get(listPosition).getCityId()));
					usernameComboBox.setSelectedItem(usernames.get(students.get(listPosition).getUserId()));
				}
			}
		});
		
		lastBtn.setBounds(227, 350, 49, 40);
		contentPane.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(24, 324, 303, 2);
		contentPane.add(separator);
		
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
						studentService.deleteStudent(id);
						JOptionPane.showMessageDialog (null, "Student was deleted successfully",
								"DELETE", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (StudentDAOException | StudentNotFoundException e1) {
					String message = e1.getMessage();
					JOptionPane.showMessageDialog (null, message, "DELETE", JOptionPane.ERROR_MESSAGE);
				}	
			}
			
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		deleteBtn.setBounds(171, 400, 108, 58);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminUpdateDeleteStudentsForm().setVisible(false);
				Main.getStudentsMenu().setEnabled(true);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(316, 440, 108, 58);
		contentPane.add(closeBtn);
		
		JButton updateBtn = new JButton("Ενημέρωση");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentUpdateDTO dto;
//				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
//						|| usernameComboBox.getSelectedItem() == null) {
//					JOptionPane.showMessageDialog(null, "Please select gender / city / username", "Gender", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
				String id = idTxt.getText();
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String birthdate = birthDateTxt.getText().trim();
				int cityId =  (Integer) cityComboBox.getSelectedItem();
				int usernameId = (Integer) usernameComboBox.getSelectedItem();


				if (lastname.equals("") || firstname.equals("") || id.equals("")) {
					JOptionPane.showMessageDialog(null, "Not valid input", "UPDATE ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {

					dto = new StudentUpdateDTO();
					dto.setId(Integer.parseInt(id));
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setBirthDate(birthdate);
					dto.setGender(gender);
					dto.setCityId(cityId);
					dto.setUsernameId(usernameId);
					Student student =studentService.updateStudent(dto);
					JOptionPane.showMessageDialog(null, "Student "
							+ " was updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
				} catch (StudentDAOException | StudentNotFoundException ex) {
					String message = ex.getMessage();
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		updateBtn.setBounds(60, 400, 108, 58);
		contentPane.add(updateBtn);
	}
}
