package ui����;

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
public class ��½ extends JFrame {
	public static  String name = "";
	 JPanel panel = new JPanel();    
	 JLabel userLabel = new JLabel("User:");
	 JTextField userText = new JTextField(20);
	 JLabel passwordLabel = new JLabel("Password:");
	 JButton loginButton = new JButton("��½"); 
	 JPasswordField passwordText = new JPasswordField(20);
	 public ��½() { 
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
				// ʵ����һ������
				JFrame f = new JFrame("�ɻ���ս");
				// ��World�����õ�������
				f.add(w);
				// �������ô��ڵĿ�͸�
				f.setSize(400, 700);
				// ���ô��ڹر�ʱ�������
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// ���ô��ھ���
				f.setLocationRelativeTo(null);
				// ��ʾ���ڣ��Զ����������paint
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


	      
