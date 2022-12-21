package org.example;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class CandleStick extends JComponent {
  double[] cordX=Home.cordX;
  double[] high=Home.high;
  double[] open=Home.open;
  double[] close=Home.close;
  double[] low=Home.low;
  int margin = 60;
  @Override
  protected void paintComponent(Graphics gph) {
    super.paintComponent(gph);
    Graphics2D graph = (Graphics2D) gph;
    int height = getHeight();
    int width = getWidth();
    graph.draw(new Line2D.Double(margin, height-margin, width-margin-50, height-margin));
    graph.draw(new Line2D.Double(margin, margin, margin, height-margin));
    double lowest = Home.findSmallest();
    double highest = Home.findGreatest();
    int counter = 0;
    for (int i = 0; i <= 20; ++i) {
      graph.draw(new Line2D.Double(margin+counter, margin+50, margin+counter, height-margin));//y-axis
      counter+=20;
    }
    counter = 0;
    for (int i = 0; i <= 13; ++i) {
      graph.draw(new Line2D.Double(margin, height-margin-counter, width-margin-150, height-margin-counter));//x-axis
      counter+=20;
    }
    for(int i = 0; i < cordX.length; ++i) {
      open[i] = map(open[i], lowest, highest, margin, height-margin);
      close[i] = map(close[i], lowest, highest, margin, height-margin);
      low[i] = map(low[i], lowest, highest, margin, height-margin);
      high[i] = map(high[i], lowest, highest, margin, height-margin);
      if(open[i] <= close[i]) {
        graph.setPaint(Color.green);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-low[i], margin+cordX[i], height-margin-open[i]));
        graph.fillRect(margin+(int)cordX[i]-4, height-margin-(int)close[i],5, (int)(close[i]-open[i]));
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-close[i], margin+cordX[i], height-margin-high[i]));
      } else if (open[i] > close[i]) {
        graph.setColor(Color.red);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-low[i], margin+cordX[i], height-margin-close[i]));
        graph.fillRect(margin+(int)cordX[i]-5, height-margin-(int)open[i],5, (int)(open[i]-close[i]));
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-open[i], margin+cordX[i], height-margin-high[i]));
      }
    }
  }
  double map(double x, double in_min, double in_max, double out_min, double out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
  }

}
