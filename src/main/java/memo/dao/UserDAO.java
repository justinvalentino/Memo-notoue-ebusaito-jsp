package main.java.memo.dao;

import memo.db.DBConnection;
import memo.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Mencari user berdasarkan email.
     * Digunakan untuk login ATAU mengecek apakah email sudah terdaftar.
     */
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); // Password yang sudah di-hash
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                user.setUpdatedAt(rs.getTimestamp("updatedAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // null jika tidak ditemukan
    }

    /**
     * Membuat user baru di database (Registrasi).
     * Password HARUS sudah di-hash sebelum memanggil fungsi ini.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (name, email, password, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, NOW(), NOW())";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // Kirim password yang sudah di-hash
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            // Tangani jika email duplikat
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mendapatkan user berdasarkan ID.
     * Dibutuhkan untuk mengambil data user yang sedang login.
     */
    public User findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                user.setUpdatedAt(rs.getTimestamp("updatedAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Meng-update informasi nama dan email user.
     */
    public boolean updateUserInfo(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, updatedAt = NOW() WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Meng-update password user.
     */
    public boolean updateUserPassword(int userId, String newHashedPassword) {
        String sql = "UPDATE users SET password = ?, updatedAt = NOW() WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newHashedPassword);
            stmt.setInt(2, userId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}