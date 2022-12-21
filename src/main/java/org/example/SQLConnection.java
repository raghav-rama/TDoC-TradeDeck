package org.example;

import java.sql.*;

public class SQLConnection {
  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    Connection con = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://" + EnvVar.getDotenv().get("REMOTE_URL"), EnvVar.getDotenv().get("REMOTE_USERNAME"), EnvVar.getDotenv().get("REMOTE_PASSWORD"));
    return con;
  }
}
