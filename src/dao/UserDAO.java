package dao;

import database.DBConnection;
import models.Paginate;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        conn = DBConnection.getConnection();
    }

    public void store (User user) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO users (name, phone, status) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getStatus());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getUsersSize (String query) {
        int size = 0;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                size++;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    public Paginate<User> getUsers(int perPage, int page){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        int usersSize = getUsersSize(query);

        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            query = query + " LIMIT ?, ?";
            int offset = (page - 1) * perPage;

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, offset);
            stmt.setInt(2, perPage);
            rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("phone"),
                                rs.getString("status")
                        )
                );
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Paginate<>(usersSize, page, perPage, users);
    }
}
