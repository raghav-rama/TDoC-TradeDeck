package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
  private JButton button_msg;
  private JPanel panelMain;

  private JPanel panelLogin;
  private JTextField usernameTextField;
  private JLabel usernameLabel;
  private JLabel passwordLabel;
  private JPasswordField passwordPasswordField;
  private JPanel loginBtnContainer;
  private JPanel besideSignIn;
  private JLabel infoLabel;
  private JLabel image1;
  private JLabel tradeDeckLabel;
  private JLabel descLabel;
  private JLabel spacer;
  private JButton forgetPasswordButton;

  public Login () {
//  panelMain.setBackground(Color.CYAN);
//  panelMain.setBounds(0, 0, 500, 500);
    button_msg.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Hello World!");
      }
    });

  }

  public static void main (String[] args) {
    JFrame frame = new JFrame("Login");
    frame.setContentPane(new Login().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

}
