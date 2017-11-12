/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BeanClasses.Quiz;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dumbledore
 */
public class QuizDAO {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Manadan", "manadan", "manadan");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int addQuiz(Quiz q) {
        PreparedStatement ps_insert = null, ps_find_result_id = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_find_result_id = con.prepareStatement("select max(result_id) from quiz");
                ResultSet rs = ps_find_result_id.executeQuery();
                int r_id = 1;
                while (rs.next()) {
                    r_id = rs.getInt(1) + 1;
                }
                ps_insert = con.prepareStatement("insert into quiz (result_id, topic_id, number_of_questions, level, duration, user_name, score, time,) values (" + r_id + ",?, ?, ?, ?, ?, ?, ?)");
                ps_insert.setString(2, q.getTopic_id());
                ps_insert.setInt(3, q.getNumber_of_questions());
                ps_insert.setInt(4, q.getLevel());
                ps_insert.setInt(5, q.getDuration());
                ps_insert.setString(6, q.getUser_name());
                ps_insert.setInt(7, q.getScore());
                ps_insert.setTimestamp(8, q.getTime());
                status = ps_insert.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_insert.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static String viewAllQuizzesByTopic(String topic_id) {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Student's User Name</td><td>Level</td><td>Duration(in minutes)</td><td>Number of Questions</td><td>Score</td><td>Time</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_topic = con.prepareStatement("select * from Quiz where topic_id = '" + topic_id + "' order by result_id");
            rs = ps_view_all_Quizzes_by_topic.executeQuery();
            int i = 1;
            while (rs.next()) {
                html_code += "<tr>\n"
                        + "         <td>" + i + "</td><td>" + rs.getString(6) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(7) + "</td><td>" + rs.getString(8) + "</td>"
                        + "     </tr>";
                i++;
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }

    public static String viewAllQuizzesByStudent(String user_name) {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Topic</td><td>Level</td><td>Duration(in minutes)</td><td>Number of Questions</td><td>Score</td><td>Time</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_student = con.prepareStatement("select * from Quiz where user_name = '" + user_name + "' order by result_id");
            rs = ps_view_all_Quizzes_by_student.executeQuery();
            int i = 1;
            while (rs.next()) {
                PreparedStatement ps_get_topic_name = con.prepareStatement("select topic_name from Topics where topic_id = '" + rs.getString(2) + "'");
                ResultSet rs1 = ps_get_topic_name.executeQuery();
                while (rs1.next()) {
                    html_code += "<tr>\n"
                            + "         <td>" + i + "</td><td>" + rs1.getString(1) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(7) + "</td><td>" + rs.getString(8) + "</td>"
                            + "     </tr>";
                    i++;
                }
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }

    public static String viewAllQuizzes(String topic_id) {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Student's User Name</td><td>Topic</td><td>Level</td><td>Duration(in minutes)</td><td>Number of Questions</td><td>Score</td><td>Time</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_topic = con.prepareStatement("select * from Quiz order by result_id");
            rs = ps_view_all_Quizzes_by_topic.executeQuery();
            int i = 1;
            while (rs.next()) {
                html_code += "<tr>\n"
                        + "         <td>" + i + "</td><td>" + rs.getString(6) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(7) + "</td><td>" + rs.getString(8) + "</td>"
                        + "     </tr>";
                i++;
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }

}
