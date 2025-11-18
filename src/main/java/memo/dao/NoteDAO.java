package main.java.memo.dao;

import main.java.memo.db.DBConnection;
import main.java.memo.models.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    public List<Note> getNotesByUserId(int userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE usersId = ? AND isDeleted = false ORDER BY updatedAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId); // Set the '?' parameter
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                note.setArchived(rs.getBoolean("isArchived"));
                note.setDeleted(rs.getBoolean("isDeleted"));
                note.setUsersId(rs.getInt("usersId"));
                note.setCategoriesId(rs.getInt("categoriesId")); // Will be null if 0
                note.setCreatedAt(rs.getTimestamp("createdAt"));
                note.setUpdatedAt(rs.getTimestamp("updatedAt"));
                
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
}