import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class DataServlet extends HttpServlet {

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws IOException, ServletException
  {
    response.setContentType("text/html");

    HttpSession httpSession = request.getSession();
    int level = (Integer) httpSession.getAttribute("level");

    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Tests</title>");
    out.println("</head>");
    out.println("<body>");

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseUtil.getConnection();
      preparedStatement = connection.prepareStatement("select * from tests t where t.level <= ?");
      preparedStatement.setInt(1, level);
      resultSet = preparedStatement.executeQuery();
      while(resultSet.next())
      {
        out.println("<form action=\"/questionServlet\" method=\"get\">");
        out.println("<input type=\"text\" name=\"testId\" value=\""+resultSet.getInt(1)+"\"/>");
        out.println("<input type=\"submit\" value=\""+resultSet.getString(2)+" Level "
            +resultSet.getInt(3)+"\"/>");
        out.println("</form>");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }

    out.println("</body>");
    out.println("</html>");
  }
}


 