/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BeanClasses.Question;
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
public class QuestionsDAO {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Manadan", "manadan", "manadan");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int addQuestion(Question q) {
        PreparedStatement ps_insert = null, ps_find_question_id = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_find_question_id = con.prepareStatement("select max(question_id) from questions");
                ResultSet rs = ps_find_question_id.executeQuery();
                int q_id = 1;
                while (rs.next()) {
                    q_id = rs.getInt(1) + 1;
                }
                ps_insert = con.prepareStatement("insert into questions (question_id, topic_id, question_text, option_a, option_b, option_c, option_d, correct_answer, level) values (" + q_id + ",?, ?, ?, ?, ?, ?, ?, ?)");
                ps_insert.setString(2, q.getTopic_id());
                ps_insert.setString(3, q.getQuestion_text());
                ps_insert.setString(4, q.getOption_a());
                ps_insert.setString(5, q.getOption_b());
                ps_insert.setString(6, q.getOption_c());
                ps_insert.setString(7, q.getOption_d());
                ps_insert.setString(8, q.getCorrect_answer());
                ps_insert.setInt(9, q.getLevel());
                status = ps_insert.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_insert.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int editQuestion(Question q) {
        PreparedStatement ps_edit = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_edit = con.prepareStatement("update Questions set topic_id = '" + q.getTopic_id() + "', question_text = '" + q.getQuestion_text() + "', option_a = '" + q.getOption_a() + "', option_b = '" + q.getOption_b() + "', option_c = '" + q.getOption_c() + "', option_d = '" + q.getOption_d() + "', correct_answer = '" + q.getCorrect_answer() + "', level = " + q.getLevel() + " where question_id = '" + q.getQuestion_id() + "'");
                status = ps_edit.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_edit.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int deleteQuestion(Question q) {
        PreparedStatement ps_delete = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_delete = con.prepareStatement("delete from Questions where Question_id = '" + q.getQuestion_id() + "'");
                status = ps_delete.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_delete.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static String viewAllQuestions(String topic_id) {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Question Text</td><td>Option A</td><td>Option B</td><td>Option C</td><td>Option D</td><td>Correct Answer</td><td>Level</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Questions = con.prepareStatement("select * from Questions where topic_id = '" + topic_id + "' order by Question_id");
            rs = ps_view_all_Questions.executeQuery();
            int i = 1;
            while (rs.next()) {
                html_code += "<tr>\n"
                        + "         <td>" + i + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(6) + "</td><td>" + rs.getString(7) + "</td><td>" + rs.getString(8) + "</td><td>" + rs.getInt(9) + "</td>"
                        + "     </tr>";
                i++;
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(QuestionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }
}
