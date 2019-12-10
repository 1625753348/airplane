package ui界面;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.pencil.airplane.World;
import com.pencil.jdbc.dao;
public class 登陆 extends JFrame {
	public static  String name = "";
	 JPanel panel = new JPanel();    
	 JLabel userLabel = new JLabel("User:");
	 JTextField userText = new JTextField(20);
	 JLabel passwordLabel = new JLabel("Password:");
	 JButton loginButton = new JButton("登陆"); 
	 JPasswordField passwordText = new JPasswordField(20);
	 public 登陆() { 
		 this.setSize(650, 400);
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel); 
		panel.setLayout(null); 
		
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
	       this.setVisible(true);
	       this.setLocationRelativeTo(null);
	       loginButton.addActionListener(new action());
	}
	 class action implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() ==  loginButton) {
			
			
			dao Db = new dao();
			if(Db.select(userText.getText(),Integer.parseInt(passwordText.getText())))
			{
				name = userText.getText();
				
				
				World w = new World();
				// 实例化一个窗口
				JFrame f = new JFrame("飞机大战");
				// 将World类设置到窗口中
				f.add(w);
				// 首先设置窗口的宽和高
				f.setSize(400, 700);
				// 设置窗口关闭时程序结束
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// 设置窗口居中
				f.setLocationRelativeTo(null);
				// 显示窗口，自动调用上面的paint
				f.setVisible(true);

				w.start();
			}
			else {
				System.out.println("d1");
			}
		}
		
		
		
	} 
}
}


	      
