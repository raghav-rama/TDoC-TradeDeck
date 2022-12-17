package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.DoubleStream;

public class Home {
  private JButton button1;
  private JButton button2;
  private JButton button3;
  private JTabbedPane tabbedPane1;
  private JPanel tab1;
  private JPanel tab2;
  private JPanel tab3;
  private JTabbedPane tabbedPane2;
  private JPanel candleStick;
  private JPanel raw;
  private JPanel panel1;
  private JPanel panelMain;
  private CandleStick candleStick1;
  private JLabel tradeDeck;
  private Graph graph1;
  private JFormattedTextField formattedTextField1;
  private JButton searchButton;
  private JPanel resultContainer;
  private JButton[] resultButtons;

  public Home() {

  button1.addActionListener(e -> tabbedPane1.setSelectedIndex(0));
  button3.addActionListener(e -> tabbedPane1.setSelectedIndex(1));
  button2.addActionListener(e -> tabbedPane1.setSelectedIndex(2));

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        resetButtons();
        String search = formattedTextField1.getText();
        String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="
            + search
            + "&apikey=XYMD6F46NUUEPLX0";
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        try {
          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//          System.out.println("\n"+response.body());
          JSONObject obj = new JSONObject(response.body());
          JSONArray searchResults = obj.getJSONArray("bestMatches");
          int len = searchResults.length();
//          System.out.println("\nlen: "+len);

          float[] matchScore = new float[len];
          String[] val = new String[len];

          for(int i = 0; i < len; ++i) {
            JSONObject kvObj = new JSONObject(searchResults.get(i).toString());
            matchScore[i] = kvObj.getFloat("9. matchScore");
            val[i] = kvObj.getString("1. symbol") + " (" + kvObj.getString("2. name") + ")";
            resultButtons[i].setToolTipText(val[i]);
            val[i] = reduceString(val[i]); // reduce length so button size doesn't overflow
          }
          if(len > 10) {
              len = 10;
            }
          bubbleSort(val, matchScore);
          for(int i = 0; i < len; ++i) {
            resultButtons[i].setText(val[i]);
            resultButtons[i].setPreferredSize(new Dimension(100, 25));
            resultButtons[i].setVisible(true);
          }
        } catch (IOException | InterruptedException ex) {
          System.err.println();
        }
      }
    });
  }

  public static double[] fetch(String searchExp) throws IOException, InterruptedException {
    String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" +
        searchExp +
        "&interval=5min&apikey=XYMD6F46NUUEPLX0";
    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String info = response.body();
    JSONObject obj = new JSONObject(info);
    System.out.println("obj: "+obj);
    JSONObject time = obj.getJSONObject("Time Series (5min)");
//    System.out.println("time: "+time);
    Set<String> keys = time.keySet();
    String[] dates = keys.toArray(new String[0]);
    Arrays.sort(dates);
//    System.out.println("dates: "+ Arrays.toString(dates));
    String[] close = new String[keys.size()];
    for(int i=0;i<close.length;++i)
      close[i] = time.getJSONObject(dates[i]).getString("4. close");
//    System.out.println("closing val: "+Arrays.toString(close));
//    Arrays.stream(close).flatMapToDouble(e -> DoubleStream.of(Double.parseDouble(e))).forEach(e -> System.out.print(e+" "));
    return Arrays.stream(close).mapToDouble(Double::parseDouble).toArray();
  }

  public static void main (String[] args) throws IOException, InterruptedException {
    JFrame frame = new JFrame("Home");
    frame.setContentPane(new Home().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

  }

  private void createUIComponents() {
    candleStick1 = new CandleStick();
    resultContainer = new JPanel();
    resultContainer.setLayout(new GridBagLayout());
    int len=10;
    resultButtons = new JButton[len];
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.ipady=5;
    gbc.insets=new Insets(5,0,5,0);
    gbc.weightx=.9;
    gbc.anchor=GridBagConstraints.NORTH;
    for(int i = 0; i < resultButtons.length; ++i) {
      resultButtons[i] = new JButton();
      resultButtons[i].setVisible(false);
      resultButtons[i].setSize(200,25);
      gbc.fill=GridBagConstraints.HORIZONTAL;
      gbc.gridx=0;
      gbc.gridy=i;
      resultContainer.add(resultButtons[i], gbc);
      resultButtons[i].setText("...");
      int finalI = i;
      resultButtons[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e) {
          try {
            tabbedPane1.setSelectedIndex(1);
            tabbedPane2.setSelectedIndex(1);
            String buf = resultButtons[finalI].getText();

            buf = buf.substring(0, buf.indexOf('(')-1);
            System.out.println(buf);
            graph1.cordY = fetch(buf);


          } catch (IOException | InterruptedException ex) {
            System.err.println();
          }
//          JOptionPane.showMessageDialog(null, "Hello World! "+ finalI);
        }
      });
    }


  }
  public void bubbleSort(String[] arr1, float[] arr2) {
    for(int i = 0; i < arr2.length - 1; ++i) {
      for (int j = 0; j < arr2.length -1 -i; ++i) {
        if(arr2[j]<arr2[j+1]) {
          float t = arr2[j];
          arr2[j] = arr2[j+1];
          arr2[j+1] = t;

          String s = arr1[j];
          arr1[j] = arr1[j+1];
          arr1[j+1] = s;
        }
      }
    }
//    System.out.println(Arrays.toString(arr1));
//    System.out.println(Arrays.toString(arr2));
  }

  void resetButtons() {
    for(int i = 0; i < resultButtons.length; ++i)
      resultButtons[i].setVisible(false);
  }

  String reduceString(String s) {
    if(s.length() > 36) {
      return s.substring(0, 33) + "...";
    } else {
      return s;
    }

  }


}
