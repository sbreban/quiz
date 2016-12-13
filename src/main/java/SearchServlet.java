import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;


public class SearchServlet extends HttpServlet {

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws IOException, ServletException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String column = request.getParameter("column");
    boolean isInt = false;
    String value = request.getParameter("value");
    if(column.equals("age") || column.equals("id"))
      isInt = true;

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Database</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<table border=1 style="+"width:100%"+">");
    out.println("<tr bgcolor=#32BD9D>\n"
        +"<td>ID</td>\n"
        +"<td>Name</td>\n"
        +"<td>E-Mail</td>\n"
        +"<td>Age</td>"
        +"<td>HomeTown</td>"
        +"</tr>");

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseUtil.getConnection();
      if (isInt) {
        preparedStatement = connection.prepareStatement("select * from users where " + column + "=" + Integer.parseInt(value));
      } else {
        preparedStatement = connection.prepareStatement("select * from users where " + column + "='" + value + "'");
      }

      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        out.println("<tr bgcolor=#32BD9D>\n"
            +"<td>"+""+resultSet.getInt("id")+"</td>\n"
            +"<td>"+resultSet.getString("name")+"</td>\n"
            +"<td>"+resultSet.getString("email")+"</td>\n"
            +"<td>"+""+resultSet.getInt("age")+"</td>\n"
            +"<td>"+resultSet.getString("hometown")+"</td>\n"
            +"</tr>\n");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      DatabaseUtil.closeResultSet(resultSet);
      DatabaseUtil.closePreparedStatement(preparedStatement);
      DatabaseUtil.closeConnection(connection);
    }

    out.println("</body>");
    out.println("</html>");
  }
}


 