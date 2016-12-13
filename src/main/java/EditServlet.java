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


public class EditServlet extends HttpServlet {

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Edit</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<form action=\"/edit\" method=\"post\">");
    HttpSession httpSession = request.getSession();
    String userName = (String) httpSession.getAttribute("user");

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseUtil.getConnection();
      preparedStatement = connection.prepareStatement("select * from users where user_name=?");
      preparedStatement.setString(1, userName);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        out.println("Name: <input type=\"text\" name=\"name\" value=\""+resultSet.getString("name")+"\"/> <br/><br/>");
        out.println("E-mail: <input type=\"text\" name=\"email\" value=\""+resultSet.getString("email")+"\"/> <br/><br/>");
        out.println("Age: <input type=\"text\" name=\"age\" value=\""+resultSet.getString("age")+"\"/> <br/><br/>");
        out.println("Hometown: <input type=\"text\" name=\"hometown\" value=\""+resultSet.getString("hometown")+"\"/> <br/><br/>");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }
    out.println("<input type=\"submit\" value=\"Edit\"/>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    String name=request.getParameter("name");
    String email=request.getParameter("email");
    String ageString=request.getParameter("age");
    int age=Integer.parseInt(ageString);
    String hometown=request.getParameter("hometown");
    HttpSession httpSession = request.getSession();
    String userName = (String) httpSession.getAttribute("user");

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseUtil.getConnection();
      preparedStatement = connection.prepareStatement("update users set name=?, email=?, age=?, hometown=? where user_name=?");
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, email);
      preparedStatement.setInt(3, age);
      preparedStatement.setString(4, hometown);
      preparedStatement.setString(5, userName);
      preparedStatement.executeUpdate();
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/loginSuccess.jsp");
      requestDispatcher.forward(request, resp);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }
  }
}


 