package ui½çÃæ;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.pencil.jdbc.dao;
class jf extends JFrame {
	 JPanel panel = new JPanel();    
	 JLabel userLabel = new JLabel("User:");
	 JTextField userText = new JTextField(20);
	 JLabel passwordLabel = new JLabel("Password:");
	 JButton loginButton = new JButton("×¢²á"); 
	 JPasswordField passwordText = new JPasswordField(20);
 
	 public jf() { 
		 this.setSize(650, 366);
		 this.setUndecorated(true);
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel); 
		panel.setLayout(null); 
		
		//±³¾°Í¼Æ¬
		panel.setPreferredSize(new Dimension(0,140));
		ImageIcon image = new ImageIcon("shoot/222.jpg");
		JLabel bakgroud = new JLabel(image);
		
		JButton out = new JButton(new ImageIcon("shoot/out¡¢.jpg"));
		out.setBorderPainted(false);
		panel.add(out);
		
		out.setBounds(610, 0, 30, 30);
		bakgroud.setBounds(0, 0, 650, 366);
		panel.add(bakgroud);
		
		
		
		userLabel.setBounds(10,20,80,25);
	        panel.add(userLabel);
	        
	         userText.setBounds(100,20,165,25);
	        panel.add(userText);
	        
	           passwordLabel.setBounds(10,50,80,25);
	        panel.add(passwordLabel);
	        
	       
	        passwordText.setBounds(100,50,165,25);
	        panel.add(passwordText); 
	        
	        loginButton.setBounds(10, 80, 80, 25);
	        panel.add(loginButton);
	       this.setLocationRelativeTo(null);
	       this.setVisible(true);
	       
	       loginButton.addActionListener(new action());
	}
	 class action implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() ==  loginButton) {
			
			
			dao Db = new dao();
			Db.insert(userText.getText(),Integer.parseInt(passwordText.getText()));
			
			ui½çÃæ.µÇÂ½ r = new µÇÂ½();
			
			
		}
		
		
		
	} 
}
}

public class ×¢²á {

	public static void main(String[] args) {
		
		jf j = new jf();
		
	}
}
	      
