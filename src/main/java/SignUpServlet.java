import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class SignUpServlet
 */

public class SignUpServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher rd;
    String username=request.getParameter("username");
    String password=request.getParameter("password");
    String name=request.getParameter("name");
    String email=request.getParameter("email");
    String ageString=request.getParameter("age");
    int age=Integer.parseInt(ageString);
    String hometown=request.getParameter("hometown");

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseUtil.getConnection();
      preparedStatement = connection.prepareStatement("insert into users (name, email, age,hometown,user_name, password) " +
          "values('"+name+"','"+email+"',"+age+",'"+hometown+"','"+username+"','"+password+"')");
      preparedStatement.executeUpdate();
    } catch(SQLException ex) {
      ex.printStackTrace();
    } finally {
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }

    rd = request.getRequestDispatcher("/index.jsp");
    rd.forward(request, response);
  }
}
