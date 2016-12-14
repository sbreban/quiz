import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

    List<Question> questionList = new ArrayList<Question>();
    try {
      questionList = DatabaseUtil.getQuestions(testId);
      out.println("<form action=\"/questionServlet\" method=\"post\">");
      for (int i = 0; i < questionList.size(); i++) {
        Question question = questionList.get(i);
        out.println("<p><input type=\"text\" name=\"question" + i + "\" width=\"20\" value=\"" + question.getId() + "\"/>");
        out.println(question.getQuestion() + "<br>");
        out.println("<input type=\"radio\" name=\"answer" + i + "\" value=\"" + question.getCorrectAnswer() + "\">"
            + question.getCorrectAnswer() + "<br>");
        out.println("<input type=\"radio\" name=\"answer" + i + "\" value=\"" + question.getWrongAnswer() + "\">"
            + question.getWrongAnswer() + "<br>");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    out.println("No questions: <input type=\"text\" name=\"noQuestions\" value=\"" + questionList.size() + "\"><br>");
    out.println("<input type=\"submit\" value=\"Check answers\">");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String noQuestionsString = req.getParameter("noQuestions");
    int noQuestion = Integer.parseInt(noQuestionsString);

    boolean allCorrect = true;

    try {
      for (int i = 0; i < noQuestion; i++) {
        String questionIdString = req.getParameter("question" + i);
        int questionId = Integer.parseInt(questionIdString);
        String answer = req.getParameter("answer" + i);
        boolean correct = DatabaseUtil.checkAnswer(questionId, answer);
        allCorrect = allCorrect && correct;
      }
//      if (allCorrect && questionId != -1) {
//        preparedStatement = connection.prepareStatement("SELECT q.test_id FROM questions q WHERE q.id=?");
//        preparedStatement.setInt(1, questionId);
//        resultSet = preparedStatement.executeQuery();
//        int testId = -1;
//        if (resultSet.next()) {
//          testId = resultSet.getInt(1);
//        }
//        preparedStatement = connection.prepareStatement("SELECT u.tests FROM users u WHERE u.name=?");
//        preparedStatement.setString(1, (String) session.getAttribute("user"));
//        resultSet = preparedStatement.executeQuery();
//        String completed = null;
//        boolean somethingNew = false;
//        if (resultSet.next()) {
//          completed = resultSet.getString(1);
//          if (!completed.contains(testId + "")) {
//            somethingNew = true;
//            if (completed.isEmpty()) {
//              completed = testId + "";
//            } else {
//              completed += ";" + testId;
//            }
//          }
//        }
//        if (completed == null) {
//          completed = testId + "";
//        }
//        preparedStatement = connection.prepareStatement("UPDATE users u SET u.tests=? WHERE u.user_name=?");
//        preparedStatement.setString(1, completed);
//        preparedStatement.setString(2, (String) session.getAttribute("user"));
//        preparedStatement.execute();
//        if (somethingNew) {
//          int level = (Integer) session.getAttribute("level");
//          level++;
//          session.setAttribute("level", level);
//        }
//      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    RequestDispatcher rd = req.getRequestDispatcher("/loginSuccess.jsp");
    rd.forward(req, resp);
  }
}
