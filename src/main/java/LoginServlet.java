import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    doPost(req, res);
  }

  String selectUser = "select * from users u " +
      "where u.user_name=? and u.password=?";

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    RequestDispatcher rd;
    String login = req.getParameter("name");
    String password = req.getParameter("password");

    PreparedStatement userPreparedStatement = null;
    PreparedStatement testsPreparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;
    ResultSet testResultSet = null;

    try {
      connection = DatabaseUtil.getConnection();
      userPreparedStatement = connection.prepareStatement(selectUser);
      userPreparedStatement.setString(1, login);
      userPreparedStatement.setString(2, password);
      resultSet = userPreparedStatement.executeQuery();
      if (resultSet.next()) {
        HttpSession session = req.getSession();
        session.setAttribute("user", login);
        session.setMaxInactiveInterval(60);
        String completedTests = resultSet.getString(4);
        int level = 1;
        if (completedTests != null) {
          String testIds[] = completedTests.split(";");
          String sql = "select t.level from tests t " +
              "where t.id in (";
          for (int i = 0; i < testIds.length-1; i++) {
            sql += testIds[i]+",";
          }
          sql += testIds[testIds.length-1]+")";
          testsPreparedStatement = connection.prepareStatement(sql);
          testResultSet = testsPreparedStatement.executeQuery();
          while (testResultSet.next()) {
            int testLevel = testResultSet.getInt(1);
            if (testLevel > level) {
              level = testLevel;
            }
          }
        }
        session.setAttribute("level", level);
        rd = req.getRequestDispatcher("/loginSuccess.jsp");
      } else {
        rd = req.getRequestDispatcher("/loginError.jsp");
      }
      rd.forward(req, res);
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closeResultSet(testResultSet);
      DatabaseUtil.closePreparedStatement(testsPreparedStatement);
      DatabaseUtil.closePreparedStatement(userPreparedStatement);
      DatabaseUtil.closeConnection(connection);
    }
  }
}