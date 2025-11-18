package main.java.memo.dao; // <-- Bagian 1: Deklarasi Package

// <-- Bagian 2: Semua Import yang Dibutuhkan -->
import memo.db.DBConnection;
import memo.models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// CREATE
public boolean createCategory(Category category) {
    String sql = "INSERT INTO categories (name, usersId) VALUES (?, ?)";
    try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, category.getName());
        stmt.setInt(2, category.getUsersId());
        
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// UPDATE
public boolean updateCategory(Category category) {
    String sql = "UPDATE categories SET name = ? WHERE id = ? AND usersId = ?";
    try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, category.getName());
        stmt.setInt(2, category.getId());
        stmt.setInt(3, category.getUsersId()); // Security check
        
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// DELETE
public boolean deleteCategory(int categoryId, int userId) {
    String sql = "DELETE FROM categories WHERE id = ? AND usersId = ?";
    try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, categoryId);
        stmt.setInt(2, userId);
        
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// READ
public Category getCategoryById(int categoryId, int userId) {
    String sql = "SELECT * FROM categories WHERE id = ? AND usersId = ?";
    Category category = null;
    try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, categoryId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setUsersId(rs.getInt("usersId"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return category;
}