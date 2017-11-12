/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BeanClasses.Topic;
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
public class TopicsDAO {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Manadan", "manadan", "manadan");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int addTopic(Topic t) {
        PreparedStatement ps_insert = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_insert = con.prepareStatement("insert into topics (topic_id, topic_name) values (?, ?)");
                ps_insert.setString(1, t.getTopic_id());
                ps_insert.setString(2, t.getTopic_name());
                status = ps_insert.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_insert.close();
            } catch (SQLException ex) {
                Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int editTopic(Topic t) {
        PreparedStatement ps_edit = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_edit = con.prepareStatement("update topics set topic_name = '" + t.getTopic_name() + "' where topic_id = '" + t.getTopic_id() + "'");
                status = ps_edit.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_edit.close();
            } catch (SQLException ex) {
                Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int deleteTopic(Topic t) {
        PreparedStatement ps_delete = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_delete = con.prepareStatement("delete from topics where topic_id = '" + t.getTopic_id() + "'");
                status = ps_delete.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_delete.close();
            } catch (SQLException ex) {
                Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static ArrayList<Topic> viewAllTopics() {
        ResultSet rs = null;
        ArrayList<Topic> al = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_topics = con.prepareStatement("select * from topics order by topic_id");
            rs = ps_view_all_topics.executeQuery();
            while (rs.next()) {
                al.add(new Topic(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }

    public static String getTopicName(String topic_id) {
        ResultSet rs = null;
        String ans = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_topics = con.prepareStatement("select topic_name from topics where topic_id = '" + topic_id + "'");
            rs = ps_view_all_topics.executeQuery();
            while (rs.next()) {
                ans = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

}
