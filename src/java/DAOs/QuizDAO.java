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
import java.util.ArrayList;
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
                ps_insert = con.prepareStatement("insert into quiz (result_id, topic_id, number_of_questions, level, duration, user_name, score, time) values (" + r_id + ",?, ?, ?, ?, ?, ?, ?)");
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

    public static ArrayList<Quiz> viewAllQuizzesByTopic(String topic_id) {
        ResultSet rs = null;
        ArrayList<Quiz> al = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_topic = con.prepareStatement("select * from Quiz where topic_id = '" + topic_id + "' order by result_id");
            rs = ps_view_all_Quizzes_by_topic.executeQuery();
            while (rs.next()) {
                al.add(new Quiz(rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(7), rs.getString(2), rs.getString(6), rs.getTimestamp(8)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }

    public static ArrayList<Quiz> viewAllQuizzesByStudent(String user_name) {
        ResultSet rs = null;
        ArrayList<Quiz> al = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_student = con.prepareStatement("select * from Quiz where user_name = '" + user_name + "' order by result_id");
            rs = ps_view_all_Quizzes_by_student.executeQuery();
            while (rs.next()) {
                al.add(new Quiz(rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(7), rs.getString(2), rs.getString(6), rs.getTimestamp(8)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }

    public static ArrayList<Quiz> viewAllQuizzes(String topic_id) {
        ResultSet rs = null;
        ArrayList<Quiz> al = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_Quizzes_by_topic = con.prepareStatement("select * from Quiz order by result_id");
            rs = ps_view_all_Quizzes_by_topic.executeQuery();
            while (rs.next()) {
                al.add(new Quiz(rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(7), rs.getString(2), rs.getString(6), rs.getTimestamp(8)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }

}
