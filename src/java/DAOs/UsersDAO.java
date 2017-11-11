/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BeanClasses.Topic;
import BeanClasses.User;
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
public class UsersDAO {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Manadan", "manadan", "manadan");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int addUser(User u) {
        PreparedStatement ps_insert = null, ps_get_all_user_name = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_insert = con.prepareStatement("insert into users (user_name, password, user_type) values (?, ?, ?)");
                ps_get_all_user_name = con.prepareStatement("select user_name from users");
                ResultSet rs = ps_get_all_user_name.executeQuery();
                while (rs.next()) {
                    if (rs.getString(1).equals(u.getUser_name())) {
                        status = -1; //this means that this user name already exists
                        break;
                    }
                }
                if (status == 0) {
                    ps_insert.setString(1, u.getUser_name());
                    ps_insert.setString(2, u.getPassword());
                    ps_insert.setString(3, "" + u.getUser_type());
                    status = ps_insert.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_insert.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int editUser(User u) {
        PreparedStatement ps_edit = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_edit = con.prepareStatement("update users set password = '" + u.getPassword() + "' where user_name = '" + u.getUser_name() + "'");
                status = ps_edit.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_edit.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int deleteUser(User u) {
        PreparedStatement ps_delete = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_delete = con.prepareStatement("delete from users where user_name = '" + u.getUser_name() + "'");
                status = ps_delete.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_delete.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static String viewAllStudents() {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Student's User Name</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_students = con.prepareStatement("select * from users where user_type = 'S' order by user_name");
            rs = ps_view_all_students.executeQuery();
            int i = 1;
            while (rs.next()) {
                html_code += "<tr>\n"
                        + "         <td>" + i + "</td><td><input type = \"button\" name = \"user_name\" value = \"" + rs.getString(1) + "\"></td>"
                        + "     </tr>";
                i++;
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }
    
    public static String viewAllAdmins() {
        ResultSet rs = null;
        String html_code = "<table border = 1px>\n"
                + "<tr>\n"
                + "     <td>No.</td><td>Student's User Name</td>\n"
                + "</tr>\n";
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_all_admins = con.prepareStatement("select * from users where user_type = 'A' order by user_name");
            rs = ps_view_all_admins.executeQuery();
            int i = 1;
            while (rs.next()) {
                html_code += "<tr>\n"
                        + "         <td>" + i + "</td><td>" + rs.getString(1) + "</td>"
                        + "     </tr>";
                i++;
            }
            html_code += "</table>";
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return html_code;
    }

}
