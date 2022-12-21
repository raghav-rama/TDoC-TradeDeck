package org.example;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Login extends JComponent {
  private JButton button_msg;
  private JPanel panelMain;

  private JPanel panelLogin;
  private JTextField usernameTextField;
  private JLabel usernameLabel;
  private JLabel passwordLabel;
  private JPasswordField passwordTextField;
  private JPanel loginBtnContainer;
  private JPanel besideSignIn;
  private JLabel infoLabel;
  private JLabel image1;
  private JLabel tradeDeckLabel;
  private JLabel descLabel;
  private JLabel spacer;
  private JButton forgetPasswordButton;
  private JButton registerButton;
  public static Main main;
  public static Home home;



  public Login () {


    button_msg.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          String username = usernameTextField.getText();
          String password = String.valueOf(passwordTextField.getPassword());
          String hashedPassword = hashedPassword(password);
          String query = "SELECT * FROM UserDetails WHERE Username = '"+username+"' AND Password = '"+hashedPassword+"';";
          PreparedStatement ps = SQLConnection.getConnection().prepareStatement(query);
          ResultSet rs = ps.executeQuery();
          if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Logged in Successfully");
            UserDetails.name = username;
            home.refreshUI();
            main.getTabbedPane1().setSelectedIndex(2);
          } else {
            JOptionPane.showMessageDialog(null, "Invalid Credentials");
          }
//          if(!usernameTextField.getText().equals("a"))
//            throw new Exception("bad username");
//          if(!String.valueOf(passwordTextField.getPassword()).equals("69"))
//            throw new Exception("bad password");
//          else {
//
//            panelMain.setVisible(false);
//
//          }
        } catch (Exception ex) {
          System.err.println();
        }

      }
    });
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
//          Register.main(null);
//          dispose();
//          Register register = new Register();
//          register.pack();
//          register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//          register.setVisible(true);
//          register.setLocationRelativeTo(null);
//          register.setContentPane(register.panelMain);
//          register.setSize(500, 500);
//          dispose();
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
      }
    });


    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        main.getTabbedPane1().setSelectedIndex(0);
      }
    });
  }

  public String hashedPassword(String password) {
    //Algorithms: MD5 SHA-1, SHA-256
    try {
      MessageDigest md = MessageDigest.getInstance("SHA");
      md.update(password.getBytes());
      byte[] result = md.digest();
      StringBuilder sb = new StringBuilder();
      for (byte b: result) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      System.err.println();
      return "";
    }
  }

//  public static void main (String[] args) {
//    JFrame frame = new JFrame("Login");
//    frame.setContentPane(new Login().panelMain);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.pack();
//    frame.setVisible(true);
//  }

}
