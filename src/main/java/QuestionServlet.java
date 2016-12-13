import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sergiu on 19.01.2016.
 */
public class QuestionServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");

    String testString = req.getParameter("testId");
    int testId = Integer.parseInt(testString);

    PrintWriter out = resp.getWriter();
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Tests</title>");
    out.println("</head>");
    out.println("<body>");

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int questions = 0;

    try {
      connection = DatabaseUtil.getConnection();
      preparedStatement = connection.prepareStatement("select * from questions q where q.test_id = ?");
      preparedStatement.setInt(1, testId);
      resultSet = preparedStatement.executeQuery();
      out.println("<form action=\"/questionServlet\" method=\"post\">");
      while(resultSet.next())
      {
        out.println("<p><input type=\"text\" name=\"question"+questions+"\" width=\"20\" value=\""+resultSet.getInt(1)+"\"/>");
        out.println(resultSet.getString(3)+"<br>");
        out.println("<input type=\"radio\" name=\"answer"+questions+"\" value=\""+resultSet.getString(4)+"\">"
            +resultSet.getString(4)+"<br>");
        out.println("<input type=\"radio\" name=\"answer"+questions+"\" value=\""+resultSet.getString(5)+"\">"
            +resultSet.getString(5)+"<br>");
        questions++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }

    out.println("No questions: <input type=\"text\" name=\"noQuestions\" value=\""+questions+"\"><br>");
    out.println("<input type=\"submit\" value=\"Check answers\">");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String noQuestionsString = req.getParameter("noQuestions");
    int noQuestion = Integer.parseInt(noQuestionsString);

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    boolean allCorrect = true;

    try {
      connection = DatabaseUtil.getConnection();
      HttpSession session = req.getSession();
      int questionId = -1;
      for (int i = 0; i < noQuestion; i++) {
        preparedStatement = connection.prepareStatement("SELECT * FROM questions q WHERE q.id = ? and q.correct_answer=?");
        String questionIdString = req.getParameter("question"+i);
        questionId = Integer.parseInt(questionIdString);
        String answer = req.getParameter("answer"+i);
        preparedStatement.setInt(1, questionId);
        preparedStatement.setString(2, answer);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
          allCorrect = false;
        }
      }
      if (allCorrect && questionId != -1) {
        preparedStatement = connection.prepareStatement("select q.test_id from questions q where q.id=?");
        preparedStatement.setInt(1, questionId);
        resultSet = preparedStatement.executeQuery();
        int testId = -1;
        if (resultSet.next()) {
          testId = resultSet.getInt(1);
        }
        preparedStatement = connection.prepareStatement("select u.tests from users u where u.name=?");
        preparedStatement.setString(1, (String)session.getAttribute("user"));
        resultSet = preparedStatement.executeQuery();
        String completed = null;
        boolean somethingNew = false;
        if (resultSet.next()) {
          completed = resultSet.getString(1);
          if (!completed.contains(testId + "")) {
            somethingNew = true;
            if (completed.isEmpty()) {
              completed = testId + "";
            } else {
              completed += ";" + testId;
            }
          }
        }
        if (completed == null) {
          completed = testId+"";
        }
        preparedStatement = connection.prepareStatement("update users u set u.tests=? where u.user_name=?");
        preparedStatement.setString(1, completed);
        preparedStatement.setString(2, (String)session.getAttribute("user"));
        preparedStatement.execute();
        if (somethingNew) {
          int level = (Integer) session.getAttribute("level");
          level++;
          session.setAttribute("level", level);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }

    RequestDispatcher rd = req.getRequestDispatcher("/loginSuccess.jsp");
    rd.forward(req, resp);
  }
}
