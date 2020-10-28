/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tvhtr
 */
public class DAOBook {

    DBConnection dbConn;
    Connection conn;

    public DAOBook() {
    }

    public DAOBook(DBConnection dbConn) {
        this.dbConn = dbConn;
        this.conn = dbConn.getConn();

    }

    public int insertBook(Book book) {
        int n = 0;
        String sql = "insert into Book(id,name,quantity,price) "
                + " values(?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            //pre.setDataType(index,value);
            //dataType is dataType of fiield; index is position 
            pre.setString(1, book.getId());
            pre.setString(2, book.getName());
            pre.setInt(3, book.getQuantity());
            pre.setDouble(4, book.getPrice());
            //execute
            pre.executeUpdate();
            n=1;
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void displayAll() {
        String sql = "select * from Book";
        try {

            Statement state = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(1);//rs.getString(1)
                String name = rs.getString(2);
                int quantity = rs.getInt(3);
                double price = rs.getDouble(4);

                Book book = new Book(id, name, quantity, price);
                System.out.println(book);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        DBConnection dBConnection = new DBConnection();
        DAOBook daoBook = new DAOBook(dBConnection);

        daoBook.insertBook(new Book("1","truyen tranh", 100, 100));
    }
}