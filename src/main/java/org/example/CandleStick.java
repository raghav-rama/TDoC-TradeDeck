package org.example;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class CandleStick extends JComponent {
  int[] cordX={80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320};
  int[] high={200, 175, 160, 160, 170, 100, 170, 180, 180, 100, 200, 170, 70};
  int[] open={125, 150, 150, 150, 160, 80, 50, 50, 55, 90, 80, 70, 60};
  int[] close={175, 70, 80, 125, 145, 40, 150, 160, 165, 60, 180, 150, 50};
  int[] low={100, 50, 60, 120, 140, 20, 40, 40, 45, 55, 70, 60, 20};
  int margin = 60;
  @Override
  protected void paintComponent(Graphics gph) {
    super.paintComponent(gph);
    Graphics2D graph = (Graphics2D) gph;
    int height = getHeight();
    int width = getWidth();
    graph.draw(new Line2D.Double(margin, height-margin, width-margin-50, height-margin));
    graph.draw(new Line2D.Double(margin, margin, margin, height-margin));
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
      if(open[i] <= close[i]) {
        graph.setPaint(Color.green);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-low[i], margin+cordX[i], height-margin-open[i]));
        graph.fillRect(margin+cordX[i]-5, height-margin-close[i],10, close[i]-open[i]);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-close[i], margin+cordX[i], height-margin-high[i]));

      } else if (open[i] > close[i]) {
        graph.setColor(Color.red);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-low[i], margin+cordX[i], height-margin-close[i]));
        graph.fillRect(margin+cordX[i]-5, height-margin-open[i],10, open[i]-close[i]);
        graph.draw(new Line2D.Double(margin+cordX[i], height-margin-open[i], margin+cordX[i], height-margin-high[i]));
      }

    }
  }

}
