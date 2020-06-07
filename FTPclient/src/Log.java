import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class Log extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField username;
	private JPasswordField pass;
	  private int returnCode;
	  static FTPClient client = new FTPClient();
	  String dirPath = "/rymaaaaa";//le root de mon directory
	
	public static void main(String[] args) throws UnknownHostException, IOException,ClassNotFoundException,InstantiationException,IllegalAccessException,UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Log frame = new Log();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Socket  socket=new Socket("127.0.0.1 ",21);
	}
	
	
	// methode pour returner les msg qui peut etre envoiyer par le serveur
	private static void showServerReply(FTPClient client) {
		 String[] replies = client.getReplyStrings();
		    if (replies != null && replies.length > 0) {
		        for (String aReply : replies) {
		            System.out.println("SERVER: " + aReply);
		        }
		    }
		
	}
	void fermer()
	{
		dispose();
	}


	
	

	private String read() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Create the frame.
	 */
	public Log() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 419, 315);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 102, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setFont(new Font("Arial", Font.PLAIN, 15));
		username.setBounds(112, 42, 173, 26);
		contentPane.add(username);
		username.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user=username.getText().toString();
				String password=pass.getText().toString();
			if(user.equals("ryma")&&(password.equals("rimouch")))
			{
				
				 String server = "127.0.0.1";
			        int port = 21;
			        String username = "ryma";
			        String pass = "rimouch";
			       
			        try {
						client.connect(server,port);
						 showServerReply(client);
				            int replyCode = client.getReplyCode();
				            if (!FTPReply.isPositiveCompletion(replyCode)) {
				                System.out.println("Operation failed. Server reply code: " + replyCode);
				                return;
				                }
				            boolean login = client.login(user, pass);
				            showServerReply(client);
				            
				            if (!login) {
				                System.out.println("----------    On attendre votre login ^-^   ------------ ");
				                return;
				            } else {
				            	
				                System.out.println("LOGGED IN SERVER  ^-^");
				                JOptionPane.showMessageDialog(null,"Connexion Réussite,LOGGED IN SERVER  ^-^");
				            }
				         
				            
				            ///////pour entre ou serveur est crée un repertoire///////
				           System.out.println("Connected");
				           makeDirectories(client, dirPath);
				 
				            // log out and disconnect from the server
				           // client.logout();
				          //  client.disconnect();
				 
				            System.out.println("Disconnected");
				            
					} catch (IOException e) {
						System.out.println("il y'a un probleme de connection ,véréfier !!!! ");
					
					}
			       
				Ftpcl obj=new Ftpcl();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			    fermer();
			}
				
				
			}
		});
		btnLogin.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 14));
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(204, 153, 102));
		btnLogin.setBounds(153, 173, 92, 48);
		contentPane.add(btnLogin);
		
		pass = new JPasswordField();
		pass.setBounds(112, 111, 173, 26);
		contentPane.add(pass);
		
		JLabel lblUsername = new JLabel("User_Name :");
		lblUsername.setForeground(new Color(204, 153, 102));
		lblUsername.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		lblUsername.setBounds(10, 43, 92, 26);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Pass_Word :");
		lblPassword.setForeground(new Color(204, 153, 102));
		lblPassword.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		lblPassword.setBounds(10, 111, 92, 26);
		contentPane.add(lblPassword);
		
		
	}
	 // ///////  fonction pour crée un repertoire///////////
    public static boolean makeDirectories(FTPClient Client, String dirPath)
            throws IOException {
        String[] pathElements = dirPath.split("/");
        if (pathElements != null && pathElements.length > 0) {
            for (String singleDir : pathElements) {
                boolean existed = Client.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = Client.makeDirectory(singleDir);
                    if (created) {
                        System.out.println("CREATED directory: " + singleDir);
                        Client.changeWorkingDirectory(singleDir);//C:\rymaaaaa\exemple
                        
                        FTPFile[] files = client.listFiles(dirPath);
                        for (FTPFile file : files) {
                            System.out.println(file.getName());
                        }
                    } else {
                        System.out.println("COULD NOT create directory: or directory existe " + singleDir);
                        return false;
                    }
                }
            }
        }
        return true;
    }
	}


