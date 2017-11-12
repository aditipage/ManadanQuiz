/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BeanClasses.DetailedResult;
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
public class DetailedResultDAO {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Manadan", "manadan", "manadan");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DetailedResultDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int addDetailedResult(DetailedResult d) {
        PreparedStatement ps_insert = null;
        int status = 0;
        try {
            try (Connection con = getConnection()) {
                ps_insert = con.prepareStatement("insert into detailedresult (result_id, question_id, marked_answer) values (?, ?, ?)");
                ps_insert.setInt(1, d.getResult_id());
                ps_insert.setInt(2, d.getQuestion_id());
                ps_insert.setString(3, d.getMarked_answer());
                status = ps_insert.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailedResultDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps_insert.close();
            } catch (SQLException ex) {
                Logger.getLogger(DetailedResultDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static ArrayList<DetailedResult> viewDetailedResultByResultId(int result_id) {
        ResultSet rs = null;
        ArrayList<DetailedResult> al = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement ps_view_DetailedResult = con.prepareStatement("select * from DetailedResult where result_id = '" + result_id + "' order by question_id");
            rs = ps_view_DetailedResult.executeQuery();
            while (rs.next()) {
                al.add(new DetailedResult(result_id, rs.getInt(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailedResultDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
}
