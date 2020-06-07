import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.tftp.TFTP;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextField;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;


public class Ftpcl extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	static Socket socket;
	String brows;
	String local;
	TextField textField;
	int transferMode = TFTP.BINARY_MODE;
	static InputStream in;
	static OutputStream out;
	  FTPClient client = new FTPClient();
	  String server = "127.0.0.1";
      int port = 21;
      String username = "ryma";
      String pass = "rimouch";
      FileOutputStream fos = null; 
      FileInputStream fis=null;
      private JTextField textField_1;
	
	public static void main(String[] args) throws UnknownHostException, IOException,ClassNotFoundException,InstantiationException,IllegalAccessException,UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ftpcl frame = new Ftpcl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		  socket=new Socket("127.0.0.1 ",21);//adress et port de server file zilla
			 // throw new  IOException("Erreur de connexion  serveur  FTP pas active: \n"  );//
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

	/**
	 * Create the frame.
	 */
	public Ftpcl() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 310);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 102, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectFile = new JLabel("Select File From PC :");
		lblSelectFile.setForeground(new Color(204, 153, 102));
		lblSelectFile.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		lblSelectFile.setBounds(10, 23, 152, 26);
		contentPane.add(lblSelectFile);
		
		textField1 = new JTextField();
		textField1.setFont(new Font("Arial", Font.PLAIN, 15));
		textField1.setBounds(194, 22, 352, 26);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JButton btnbrows = new JButton("Search");
		btnbrows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 // voir la listes des fichier existe dans le serveur ftp
	            try {
					client.connect(server);
					 // Enter user details : user name and password 
		            boolean isSuccess = client.login(username, pass); 
		  
		            if (isSuccess) { 
		              
		                String[] filesFTP = client.listNames(); 
		                int count = 1; 
		                // Iterate on the returned list to obtain name of each file 
		                for (String file : filesFTP) { 
		                    System.out.println("File " + count + " :" + file); 
		                
		                    count++; 
		                    showServerReply(client);
		                } 
		            } 
		            //////// afficher le contenu de serveur ftp////////
		            JFileChooser fc1 = new JFileChooser("c:/rymaaaaa/TPtest");
		            fc1.showOpenDialog(btnbrows);
					brows=fc1.getSelectedFile().getAbsolutePath();//pour ecrire le lien de votre fichier choisie// 
					textField_1.setText(brows);//pour affichier le lien dans le text field//
		           
		           // client.logout(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	         
	            ////////////////////
	        	
				/////////////////
			}
		});
		btnbrows.setForeground(new Color(204, 153, 102));
		btnbrows.setBackground(new Color(51, 0, 153));
		btnbrows.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		btnbrows.setBounds(550, 138, 107, 34);
		contentPane.add(btnbrows);
		
		JButton btnSend = new JButton("Download ");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					/////////////download fichier////////////////
					client.connect(server);
					boolean	result = client.login(username, pass);
					 client.enterLocalPassiveMode();
			            client.setFileType(FTP.BINARY_FILE_TYPE);
			 
				     File file_d = new File("D:/Ryma/cour 3.rar");//were i put the download file in my pc: destination
				    // String fileName = "chap1.pdf";//path of file in ftp server 
				     
				    String fileName = file_d.getName();
				     out = new BufferedOutputStream(new FileOutputStream(file_d));
			            boolean success = client.retrieveFile(fileName, out);
			            out.close();
			 
			            if (success) {
			                System.out.println("File has been downloaded successfully.");
			                JOptionPane.showMessageDialog(null,"success download file  ^-^ " );
			                textField_1.setText("");
			            }
			            else{
			            	System.out.println("File download failed.");
							 JOptionPane.showMessageDialog(null,"File download failed !!!!!!" );
			            }
				       
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("vérifier la taille du votre fichier !!!!!!");
				}
			
			}
		});

		btnSend.setForeground(new Color(153, 102, 102));
		btnSend.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		btnSend.setBackground(new Color(0, 0, 153));
		btnSend.setBounds(415, 191, 114, 34);
		contentPane.add(btnSend);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
						///////////upload file ////////////
						
						client.connect(server);
						boolean	result = client.login(username, pass);
						 client.setFileType(FTP.BINARY_FILE_TYPE);//le mode de trensfert des fichier/////
						
						File file = new File(local);
						//File file = new File("D:/Ryma/ryma 3eme/3/3émé anné info/examens.zip");
						String testName = file.getName();
						fis = new FileInputStream(file);
						
						// Upload file to the ftp server
						result = client.storeFile(testName, fis);
						 byte[] bytesIn = new byte[4096];
				            int read = 0;
				 
				            while ((read = fis.read(bytesIn)) != -1) {
				                fos.write(bytesIn, 0, read);
				            }
				           fis.close();
				         //  fos.close();
						if (result == true) {
							System.out.println("File is upload successfully");
							 JOptionPane.showMessageDialog(null,"success upload file  ^-^ " );
							 textField1.setText("");
						} else {
							System.out.println("File upload failed.");
							 JOptionPane.showMessageDialog(null,"File upload failed !!!!!!" );
						}
						client.logout();
					
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			
			}});
		btnUpload.setForeground(new Color(51, 0, 153));
		btnUpload.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		btnUpload.setBackground(new Color(204, 153, 102));
		btnUpload.setBounds(556, 59, 107, 34);
		contentPane.add(btnUpload);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					/////////:delete file////////
					client.connect(server);
					client.login(username, pass);
				File file=new File(brows);
					//String filename = brows;
				String filename = file.getName();
					 
					// Delete file
					 boolean exist=client.deleteFile(filename);
					//boolean exist = client.deleteFile(filename);
					 
					// Notify user for deletion 
					if (exist) {
					    System.out.println("File '"+ filename + "' deleted...");
					    JOptionPane.showMessageDialog(null,"File deleted .....");
					    textField_1.setText("");
					}
					// Notify user that file doesn't exist
					else
					    System.out.println("File '"+ filename + "' doesn't exist...");
					   JOptionPane.showMessageDialog(null," doesn't exist...");
					client.logout();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				
			}
		});
		btnDelete.setForeground(new Color(153, 102, 102));
		btnDelete.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		btnDelete.setBackground(new Color(0, 0, 153));
		btnDelete.setBounds(272, 191, 107, 34);
		contentPane.add(btnDelete);
		
		JButton btnLocal = new JButton("Local");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//pour choisie un file dija existe dans votre pc//
				JFileChooser filechoser=new JFileChooser();
				filechoser.showOpenDialog(btnLocal);
				local=filechoser.getSelectedFile().getAbsolutePath();//pour ecrire le lien de votre fichier choisie// 
				textField1.setText(local);//pour affichier le lien dans le text field//
			}
		});
		btnLocal.setForeground(new Color(0, 0, 153));
		btnLocal.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		btnLocal.setBackground(new Color(204, 153, 102));
		btnLocal.setBounds(556, 19, 107, 34);
		contentPane.add(btnLocal);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(194, 141, 352, 26);
		contentPane.add(textField_1);
		
		JLabel lblSelectFileFrom = new JLabel("Select File From Server :");
		lblSelectFileFrom.setForeground(new Color(204, 153, 102));
		lblSelectFileFrom.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		lblSelectFileFrom.setBounds(10, 142, 174, 26);
		contentPane.add(lblSelectFileFrom);
	}
}


