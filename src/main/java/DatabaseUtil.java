import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 18.01.2016.
 */
public class DatabaseUtil {

  public static String url = "jdbc:mysql://localhost:3306/";
  public static String dbName = "quiz";
  public static String driver = "com.mysql.jdbc.Driver";
  public static String userName = "alibaba";
  public static String password = "sesame";
  private static String selectUser = "select * from users u " +
      "where u.user_name=? and u.password=?";

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

  public static User getUser(String checkName, String checkPassword) {
    Connection connection = null;
    PreparedStatement userPreparedStatement = null;
    ResultSet resultSet = null;
    User user = null;

    user = new User("alibaba", "sesame", "1");

//    try {
//      connection = DatabaseUtil.getConnection();
//      userPreparedStatement = connection.prepareStatement(selectUser);
//      userPreparedStatement.setString(1, checkName);
//      userPreparedStatement.setString(2, checkPassword);
//      resultSet = userPreparedStatement.executeQuery();
//      if (resultSet.next()) {
//        String name = resultSet.getString(1);
//        String password = resultSet.getString(2);
//        String tests = resultSet.getString(3);
//        user = new User(name, password, tests);
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    } finally {
//      DatabaseUtil.closeResultSet(resultSet);
//      DatabaseUtil.closePreparedStatement(userPreparedStatement);
//      DatabaseUtil.closeConnection(connection);
//    }

    return user;
  }

  public static int getMaxLevel(String tests) {
    PreparedStatement testsPreparedStatement = null;
    Connection connection = null;
    ResultSet testResultSet = null;
    int level = 1;

//    try {
//      connection = DatabaseUtil.getConnection();
//      if (tests != null) {
//        String testIds[] = tests.split(";");
//        String sql = "select t.level from tests t " +
//            "where t.id in (";
//        for (int i = 0; i < testIds.length - 1; i++) {
//          sql += testIds[i] + ",";
//        }
//        sql += testIds[testIds.length - 1] + ")";
//        testsPreparedStatement = connection.prepareStatement(sql);
//        testResultSet = testsPreparedStatement.executeQuery();
//        while (testResultSet.next()) {
//          int testLevel = testResultSet.getInt(1);
//          if (testLevel > level) {
//            level = testLevel;
//          }
//        }
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

    return level;
  }

  public static List<Test> getTests(int maxLevel) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Test> tests = new ArrayList<Test>();

    tests.add(new Test(1, "test1", 1));

//    try {
//      connection = DatabaseUtil.getConnection();
//      preparedStatement = connection.prepareStatement("select * from tests t where t.level <= ?");
//      preparedStatement.setInt(1, maxLevel);
//      resultSet = preparedStatement.executeQuery();
//      while(resultSet.next())
//      {
//        int id = resultSet.getInt(1);
//        String name = resultSet.getString(2);
//        int level = resultSet.getInt(3);
//        Test test = new Test(id, name, level);
//        tests.add(test);
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    } finally {
//      DatabaseUtil.closeResultSet(resultSet);
//      DatabaseUtil.closePreparedStatement(preparedStatement);
//      DatabaseUtil.closeConnection(connection);
//    }

    return tests;
  }

  public static List<Question> getQuestions(int testId) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Question> questions = new ArrayList<Question>();

    questions.add(new Question(1, "cine?", "a", "b"));

//    try {
//      connection = DatabaseUtil.getConnection();
//      preparedStatement = connection.prepareStatement("select * from questions q where q.test_id = ?");
//      preparedStatement.setInt(1, testId);
//      resultSet = preparedStatement.executeQuery();
//      while(resultSet.next())
//      {
//        int questionId = resultSet.getInt(1);
//        String question = resultSet.getString(2);
//        String correctAnswer = resultSet.getString(3);
//        String wrongAnswer = resultSet.getString(4);
//        questions.add(new Question(questionId, question, wrongAnswer, correctAnswer));
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    } finally {
//      DatabaseUtil.closeResultSet(resultSet);
//      DatabaseUtil.closePreparedStatement(preparedStatement);
//      DatabaseUtil.closeConnection(connection);
//    }

    return questions;
  }

  public static boolean checkAnswer(int questionId, String answer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    boolean allCorrect = true;

    try {
      connection = DatabaseUtil.getConnection();
      for (int i = 0; i < noQuestion; i++) {
        preparedStatement = connection.prepareStatement("SELECT * FROM questions q WHERE q.id = ? AND q.correct_answer=?");
        String questionIdString = req.getParameter("question" + i);
        questionId = Integer.parseInt(questionIdString);
        String answer = req.getParameter("answer" + i);
        preparedStatement.setInt(1, questionId);
        preparedStatement.setString(2, answer);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
          allCorrect = false;
        }
      }
  }
}
