package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;

import org.json.*;

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

  public static void fetch() throws IOException, InterruptedException {
    String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=XYMD6F46NUUEPLX0";
    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String info = response.body();
    JSONObject obj = new JSONObject(info);
    System.out.println("obj: "+obj);
    JSONObject time = obj.getJSONObject("Time Series (5min)");
    System.out.println("time: "+time);
    Set<String> keys = time.keySet();
    String[] dates = keys.toArray(new String[keys.size()]);
    Arrays.sort(dates);
    System.out.println("dates: "+ Arrays.toString(dates));
    String[] close = new String[keys.size()];
    for(int i=0;i<close.length;++i)
      close[i] = time.getJSONObject(dates[i]).getString("4. close");
    System.out.println("closing val: "+Arrays.toString(close));
  }

  public static void main (String[] args) throws IOException, InterruptedException {
    JFrame frame = new JFrame("Home");
    frame.setContentPane(new Home().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    fetch();
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
