package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;

public class Graph extends JPanel {
//  int[] cordX = {40, 82, 144};
  double[] cordY = Home.close;
  double[] cordX = new double[cordY.length];
  double margin = 60;

  public Graph () throws IOException, InterruptedException {}

  @Override
  protected void paintComponent(Graphics gph) {
    super.paintComponent(gph);
    Graphics2D graph = (Graphics2D) gph;
    double height = getHeight();
    double width = getWidth();
    graph.draw(new Line2D.Double(margin, height-margin, width-margin, height-margin));
    graph.draw(new Line2D.Double(margin, margin, margin, height-margin));
    graph.setPaint(Color.RED);
    int counter = 20;
    for (int i=0; i<cordX.length; ++i) {
      cordX[i] = counter;
      double x = margin + cordX[i];
      double y = height - margin - cordY[i];
      graph.fill(new Ellipse2D.Double(x, y, 5, 5));
      counter += 5;
    }
  }
}
