package gr.aueb.cf.schoolapp.controllerview;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.security.SecUtil;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import java.sql.SQLException;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Login extends JFrame {
	private static final long serialVersionUID = 123460;
	
	private JPanel contentPane;
	private JTextField usernameTxt;
	private JTextField usernameRegTxt;
	private JPasswordField passwordTxt;
	private JPasswordField passwordRegTxt;
	private JPasswordField passwordReg2Txt;

	public Login() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 319);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(241, 241, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.setBounds(0, 11, 437, 235);
		contentPane.add(tabbedPane);
		
		JPanel login = new JPanel();
		tabbedPane.addTab("Login", null, login, null);
		login.setLayout(null);
		
		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLbl.setBounds(93, 68, 71, 23);
		login.add(passwordLbl);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(93, 35, 71, 23);
		login.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setBounds(174, 35, 153, 23);
		login.add(usernameTxt);
		usernameTxt.setColumns(10);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputPassword = String.valueOf(passwordTxt.getPassword()).trim();
				String inputUsername = usernameTxt.getText();
				if (inputUsername.matches("[aA]dmin")) {
					if (inputPassword.equals(System.getenv("SCHOOL_DB_USER_PASSWD"))) {
						Main.getAdminMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(null, "Password Error" , "Error" , JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					String role = getUserRole(inputUsername, inputPassword);
					if (role == null) {
						JOptionPane.showMessageDialog(null, "Username or Password Error" , "Error" , JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (role.equals("Student")) {
						Main.getStudentsMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					} else if (role.equals("Teacher")) {
						Main.getTeachersMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					}
				}	
			}
		});
		
	
		
		loginBtn.setForeground(new Color(0, 0, 255));
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginBtn.setBounds(238, 102, 90, 40);
		login.add(loginBtn);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setBounds(174, 68, 153, 23);
		login.add(passwordTxt);
		
		JPanel register = new JPanel();
		tabbedPane.addTab("Register", null, register, null);
		register.setLayout(null);
		
		JLabel passwordRegLbl = new JLabel("Password");
		passwordRegLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordRegLbl.setBounds(101, 80, 71, 23);
		register.add(passwordRegLbl);
		
		JLabel usernameRegLbl = new JLabel("Username");
		usernameRegLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameRegLbl.setBounds(101, 45, 82, 23);
		register.add(usernameRegLbl);
		
		usernameRegTxt = new JTextField();
		usernameRegTxt.setColumns(10);
		usernameRegTxt.setBounds(182, 47, 153, 23);
		register.add(usernameRegTxt);
		
		JLabel passwordReg2Lbl = new JLabel("Confirm Password");
		passwordReg2Lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordReg2Lbl.setBounds(45, 109, 142, 23);
		register.add(passwordReg2Lbl);
		
		passwordRegTxt = new JPasswordField();
		passwordRegTxt.setBounds(182, 80, 153, 23);
		register.add(passwordRegTxt);
		
		passwordReg2Txt = new JPasswordField();
		passwordReg2Txt.setBounds(182, 110, 153, 23);
		register.add(passwordReg2Txt);
		
		JLabel roleLbl = new JLabel("Role");
		roleLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		roleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		roleLbl.setBounds(123, 21, 49, 14);
		register.add(roleLbl);
		
		JComboBox<RoleType> roleComboBox = new JComboBox<>();
		roleComboBox.setModel(new DefaultComboBoxModel(RoleType.values()));
		roleComboBox.setBounds(182, 17, 153, 23);
		register.add(roleComboBox);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.setBounds(246, 154, 90, 40);
		register.add(registerBtn);
		
		
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = usernameRegTxt.getText().trim();
				
				String password1 = String.valueOf(passwordRegTxt.getPassword()).trim();
				
				String password2 = String.valueOf(passwordReg2Txt.getPassword()).trim();
				String role = roleComboBox.getSelectedItem().toString();
				
				if (username == "" || password1 == "" || password2 == "") {
					JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				

				if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "Confirmation password not the same", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES(?, ?, ? )";
				try (Connection connection = DBUtil.getConnection();
					 PreparedStatement ps = connection.prepareStatement(sql)) {
					ps.setString(1, username);
					ps.setString(2, SecUtil.hashPassword(password1));
					ps.setString(3, role);
					ps.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});
		registerBtn.setForeground(Color.BLUE);
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
	}
	
	private String getUserRole(String inputUsername, String inputPassword) {
		PreparedStatement ps = null;
		String role = null;
		
		try (Connection conn = DBUtil.getConnection()) {
			String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
			
				ps = conn.prepareStatement(sql);
				ps.setString(1,  inputUsername);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) return null;
				String password = rs.getString("PASSWORD");
				role = rs.getString("ROLE");
				if (!SecUtil.checkPassword(inputPassword, password)) {
					return null;
				}
		} catch (SQLException e) {
				
				e.printStackTrace();
		}
		return role;
	}
}
