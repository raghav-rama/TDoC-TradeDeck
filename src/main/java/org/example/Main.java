package org.example;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import javax.swing.*;

public class Main extends JComponent {

  static {
    System.out.println("static");
  }

  private Main main;
  private JPanel panelMain;
  private JTabbedPane tabbedPane1;
  private Register register1;
  private Login login1;
  private Home home1;
  private JPanel registerPanel;
  private JPanel loginPanel;
  private JPanel homePanel;



  Main() {
    Login.main = this;
    Home.main = this;
    Register.main = this;

  }


  public JTabbedPane getTabbedPane1 () {
    return tabbedPane1;
  }

  public Register getRegister1 () {
    return register1;
  }

  public Login getLogin1 () {
    return login1;
  }

  public Home getHome1 () {
    return home1;
  }


  public JPanel getRegisterPanel () {
    return registerPanel;
  }


  public JPanel getLoginPanel () {
    return loginPanel;
  }


  public JPanel getHomePanel () {
    return homePanel;
  }


  public JPanel getPanelMain () {
    return panelMain;
  }


  public static void main (String[] args) {
    System.out.println("main");
    JFrame frame = new JFrame("TradeDeck");
    frame.setContentPane(new Main().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setSize(800, 600);
//    frame.setLayout(new AbsoluteLayout());

    frame.setVisible(true);
  }

  private void createUIComponents () {
  }
}
