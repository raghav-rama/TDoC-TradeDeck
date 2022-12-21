package org.example;

import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends JComponent {
  static double[] open = new double[100];
  static double[] close = new double[100];
  static double[] low = new double[100];
  static double[] high = new double[100];
  static double[] cordX = new double[100];
  public static Main main;


  final int margin = 60;
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
  private JButton button4;
  private JPanel graph;
  private JButton addToWatchListButton;
  private JButton[] resultButtons;

  public Home() {
    refreshUI();
    Login.home = this;

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
    button4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        main.getTabbedPane1().setSelectedIndex(1);
        UserDetails.name = null;
        refreshUI();
      }
    });
    addToWatchListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        updateWatchList();

      }
    });
  }

  public static void fetch(String searchExp) throws IOException, InterruptedException {
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
//    String[] close = new String[keys.size()];
//    String[] open = new String[keys.size()];
//    String[] low = new String[keys.size()];
//    String[] high = new String[keys.size()];
    double counter = 20.0d;
    for(int i = 0; i< close.length; ++i) {
      close[i] = Double.parseDouble(time.getJSONObject(dates[i]).getString("4. close"));
      open[i] = Double.parseDouble(time.getJSONObject(dates[i]).getString("1. open"));
      low[i] = Double.parseDouble(time.getJSONObject(dates[i]).getString("3. low"));
      high[i] = Double.parseDouble(time.getJSONObject(dates[i]).getString("2. high"));
      cordX[i] = counter;
      counter += 7.0;
    }
//    Arrays.stream(low).map(operand -> )

//    System.out.println("closing val: "+Arrays.toString(close1));
//    Arrays.stream(close1).flatMapToDouble(e -> DoubleStream.of(Double.parseDouble(e))).forEach(e -> System.out.print(e+" "));
//    close = Arrays.stream(close1).mapToInt(Integer::parseInt).toArray();
//    open = Arrays.stream(open1).mapToInt(Integer::parseInt).toArray();
//    high = Arrays.stream(high1).mapToInt(Integer::parseInt).toArray();
//    low = Arrays.stream(low1).mapToInt(Integer::parseInt).toArray();
  }

//  public static void main (String[] args) throws IOException, InterruptedException {
//    JFrame frame = new JFrame("Home");
//    frame.setContentPane(new Home().panelMain);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.pack();
//    frame.setVisible(true);
//
//  }

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
            tabbedPane2.setSelectedIndex(0);
            String buf = resultButtons[finalI].getText();
            buf = buf.substring(0, buf.indexOf('(')-1);
            UserDetails.symbol = buf;
//            System.out.println(buf);
            fetch(buf);
//            System.out.println(Arrays.toString(cordX));
          } catch (IOException | InterruptedException ex) {
            System.err.println();
          }
//          JOptionPane.showMessageDialog(null, "Hello World! "+ finalI);
        }
      });
    }


  }

  public void updateWatchList() {
    PreparedStatement ps;
    String query = "INSERT INTO Watchlist VALUES('"+UserDetails.name+"','"+UserDetails.symbol+"');";
    try {
      ps = SQLConnection.getConnection().prepareStatement(query);
      if(ps.executeUpdate() > 0) {
        JOptionPane.showMessageDialog(null, "Added to watchlist");
      } else {
        JOptionPane.showMessageDialog(null, "Failed to add to watchlist");
      }
    } catch(SQLException | ClassNotFoundException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      System.err.println();
    }
  }

  public void getWatchList() throws ClassNotFoundException, SQLException {
    UserDetails.watchlist.clear();
    PreparedStatement ps;
    ResultSet rs;
    String query = "SELECT * FROM Watchlist WHERE Username='"+UserDetails.name+"';";
    ps = SQLConnection.getConnection().prepareStatement(query);
    rs = ps.executeQuery();
    while(rs.next()) {
      UserDetails.watchlist.add(rs.getString("Stocks"));
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

  public static double findSmallest () {
    return Arrays.stream(low).min().getAsDouble();
  }
  public static double findGreatest() {
    return Arrays.stream(high).max().getAsDouble();
  }

  double map(double x, double in_min, double in_max, double out_min, double out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
  }

  public void refreshUI() {
    if(UserDetails.name == null) {
      tradeDeck.setText("Not Logged In");
    } else {
      tradeDeck.setText(UserDetails.name+", Welcome to TradeDeck");
    }
  }
}
