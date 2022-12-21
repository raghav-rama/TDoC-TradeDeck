package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.security.*;
import java.sql.*;
import java.util.logging.*;

public class Register extends JComponent {
  private JPanel panelMain;
  private JPanel imageContainer;
  private JPanel registerDetails;
  private JLabel bear;
  private JLabel bull;
  private JTextPane textPane1;
  private JTextPane registerTextPane;
  private JPanel registerHeading;
  private JPanel loginDetails;
  private JTextPane nameTextPane;
  private JTextPane usernameTextPane;
  private JTextField name;
  private JTextPane eMailTextPane;
  private JTextPane passwordTextPane;
  private JTextPane confirmPasswordTextPane;
  private JTextField username;
  private JTextField email;
  private JPasswordField password;
  private JPasswordField cnfPassword;
  private JButton registerButton;
  private JButton alreadyAUserButton;
  public static Main main;


  Register() throws BadLocationException {
    setTextToPane();
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        String _name = name.getText();
        String _username = username.getText();
        String _email = email.getText();
        String _password = String.valueOf(password.getPassword());
        String _cnfPassword = String.valueOf(cnfPassword.getPassword());
        if(!_password.equals(_cnfPassword)){
          JOptionPane.showMessageDialog(null, "Password doesn't match'");
        } else {
          String hashedPassword = hashedPassword(_password);

          PreparedStatement ps;
          String query = "INSERT INTO UserDetails VALUES('"+_name+"','"+_username+"','"+_email+"','"+hashedPassword+"');";
          try {
            ps = SQLConnection.getConnection().prepareStatement(query);
            if (ps.executeUpdate() > 0) {
              JOptionPane.showMessageDialog(null, "Account Created Successfully");
              UserDetails.name = _username;
              main.getTabbedPane1().setSelectedIndex(1);
            }
          } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println();
          }
        }
      }
    });
    alreadyAUserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        main.getTabbedPane1().setSelectedIndex(1);
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

  public void setTextToPane() {
    nameTextPane.setContentType("text/html");
    nameTextPane.setText(HTMLStringCodes.nameTextPaneString);

    usernameTextPane.setContentType("text/html");
    usernameTextPane.setText(HTMLStringCodes.userNamePaneString);

    eMailTextPane.setContentType("text/html");
    eMailTextPane.setText(HTMLStringCodes.eMailTextPaneString);

    passwordTextPane.setContentType("text/html");
    passwordTextPane.setText(HTMLStringCodes.passwordTextPaneString);

    confirmPasswordTextPane.setContentType("text/html");
    confirmPasswordTextPane.setText(HTMLStringCodes.confirmPasswordTextPaneString);

    textPane1.setContentType("text/html");
    textPane1.setText(HTMLStringCodes.textPane1String);
  }

//  public static void main (String[] args) throws BadLocationException {
//    JFrame frame = new JFrame("Register");
//    frame.setContentPane(new Register().panelMain);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.pack();
//    frame.setVisible(true);
//  }
}
