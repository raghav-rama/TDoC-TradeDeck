package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
  private JButton button1;
  private JButton button2;
  private JButton button3;
  private JTabbedPane tabbedPane1;
  private JPanel tab1;
  private JPanel tab2;
  private JPanel tab3;
  private JTabbedPane tabbedPane2;
  private JPanel raw;
  private JPanel candlestick;
  private JPanel panel1;
  private JPanel panelMain;
  private CandleStick candleStick1;
  private JLabel tradeDeck;

  public Home() {
  button1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed (ActionEvent e) {
      tabbedPane1.setSelectedIndex(0);
    }
  });
  button3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed (ActionEvent e) {
      tabbedPane1.setSelectedIndex(1);

    }
  });
  button2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed (ActionEvent e) {
      tabbedPane1.setSelectedIndex(2);

    }
  });
}
  public static void main (String[] args) {
    JFrame frame = new JFrame("Home");
    frame.setContentPane(new Home().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void createUIComponents() {
    candleStick1 = new CandleStick();
//    JFrame frame = new JFrame("Home");
//    frame.setContentPane(new Home().panelMain);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.pack();
//    frame.setVisible(true);

  }
}
