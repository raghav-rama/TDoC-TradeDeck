package org.example;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class TestJFrame extends JFrame {
  public static void main (String[] args) {
    JFrame frame = new JFrame();
    JPanel panelMain = new JPanel();
    frame.add(panelMain);
    panelMain.setLayout(new BorderLayout());
    JButton btn = new JButton("Click to close");
    panelMain.add(btn, BorderLayout.SOUTH);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    btn.addActionListener(e -> {
      try {
//        Register register = new Register();
//        register.pack();
//        register.setSize(800, 600);
//        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        register.setLocationRelativeTo(null);
//        register.setVisible(true);
//        register.setContentPane(register.panelMain);
        frame.dispose();
      } catch (Exception ex) {
        System.err.println();
      }
    });
  }
}
