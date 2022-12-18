package org.example;

import java.sql.*;

public class SQLConnection {
  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    Connection con = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/user_information", "Admin", EnvVar.getDotenv().get("MYSQL_PASSWORD"));
    return con;
  }

}
