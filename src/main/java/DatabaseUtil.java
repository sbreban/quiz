import java.sql.*;

/**
 * Created by Sergiu on 18.01.2016.
 */
public class DatabaseUtil {

  public static String url = "jdbc:mysql://localhost:3306/";
  public static String dbName = "quiz";
  public static String driver = "com.mysql.jdbc.Driver";
  public static String userName = "alibaba";
  public static String password = "sesame";

  static {
    try {
      Class.forName(driver).newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url + dbName, userName, password);
  }

  public static void closeResultSet(ResultSet resultSet) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public static void closePreparedStatement(PreparedStatement preparedStatement) {
    try {
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public static void closeConnection(Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
