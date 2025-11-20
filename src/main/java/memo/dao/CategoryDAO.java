package memo.dao;

import memo.db.DBConnection;
import memo.models.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getCategoriesByUserId(int userId) {
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM categories WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // REQUIRED BY CONTROLLER
    public Category getCategoryById(int userId, int categoryId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM categories WHERE user_id = ? AND id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, categoryId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createCategory(Category category) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO categories (user_id, name) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, category.getUserId());
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE categories SET name=? WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.setInt(3, category.getUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int userId, int categoryId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM categories WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
