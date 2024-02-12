package gr.aueb.cf.schoolapp.controllerview;

import gr.aueb.cf.schoolapp.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class AdminMenu extends JFrame {

	private static final long serialVersionUID = 123458;
	private JPanel contentPane;

	
	public AdminMenu() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));

		setTitle("Admin Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 505);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		btnNewButton.setBounds(32, 67, 40, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Εκπαιδευτές");
		lblNewLabel.setForeground(new Color(128, 64, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(82, 73, 86, 29);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		btnNewButton_1.setBounds(32, 124, 40, 40);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Εκπαιδευόμενοι");
		lblNewLabel_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(82, 130, 117, 29);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.setBounds(32, 181, 40, 40);
		contentPane.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Χρήστες");
		lblNewLabel_1_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(82, 187, 70, 29);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1.setBounds(32, 238, 40, 40);
		contentPane.add(btnNewButton_1_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Πόλεις");
		lblNewLabel_1_1_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(82, 244, 51, 29);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("");
		btnNewButton_1_1_1_1.setBounds(32, 295, 40, 40);
		contentPane.add(btnNewButton_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Ειδικότητες");
		lblNewLabel_1_1_1_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1_1.setBounds(82, 301, 86, 29);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 388, 270, 1);
		contentPane.add(separator);
		
		JButton btnNewButton_2 = new JButton("Close App");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setForeground(new Color(0, 0, 255));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(204, 417, 103, 40);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setBounds(32, 11, 40, 40);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel_2 = new JLabel("Συναντήσεις");
		lblNewLabel_2.setForeground(new Color(128, 64, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(82, 17, 86, 29);
		contentPane.add(lblNewLabel_2);
	}

}
