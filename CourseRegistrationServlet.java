import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;


public class CourseRegistrationServlet extends HttpServlet {

    // Handle POST request for course registration
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String course = request.getParameter("course");

        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO users (name, email, phone, course) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, course);
                stmt.executeUpdate();
                response.getWriter().write("Successfully registered for " + course);
            }
        } catch (SQLException e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    // Handle GET request for verifying course registration by phone number
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");

        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM users WHERE phone = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, phone);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String course = rs.getString("course");
                    response.getWriter().write("Verified: " + name + " is registered for " + course);
                } else {
                    response.getWriter().write("No registration found for this phone number.");
                }
            }
        } catch (SQLException e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
