package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
public class Graph extends JPanel {
  int[] cordX = {40, 82, 144};
  int[] cordY = {20, 42, 64};
  int margin = 60;

  @Override
  protected void paintComponent(Graphics gph) {
    super.paintComponent(gph);
    Graphics2D graph = (Graphics2D) gph;
    int height = getHeight();
    int width = getWidth();
    graph.draw(new Line2D.Double(margin, height-margin, width-margin, height-margin));
    graph.draw(new Line2D.Double(margin, margin, margin, height-margin));
    graph.setPaint(Color.RED);
    for (int i=0; i<cordX.length; ++i) {
      int x = margin + cordX[i];
      int y = height - margin - cordY[i];
      graph.fill(new Ellipse2D.Double(x, y, 10, 10));
    }
  }
}
