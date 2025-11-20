package memo.dao;

import memo.db.DBConnection;
import memo.models.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    // GET NOTES (normal)
    public List<Note> getNotesByUserId(int userId) {
        List<Note> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM notes WHERE user_id = ? AND is_archived = 0";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapNote(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // CONTROLLER CALLS: getNotesByUserId(userId, search)
    public List<Note> getNotesByUserId(int userId, String search) {
        List<Note> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM notes WHERE user_id = ? AND is_archived = 0 AND title LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, "%" + search + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapNote(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // FETCH SINGLE NOTE
    public Note getNoteById(int userId, int noteId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM notes WHERE user_id = ? AND id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, noteId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapNote(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE
    public void createNote(Note note) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO notes (user_id, category_id, title, content, is_archived) VALUES (?,?,?,?,0)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, note.getUserId());
            stmt.setInt(2, note.getCategoryId());
            stmt.setString(3, note.getTitle());
            stmt.setString(4, note.getContent());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateNote(Note note) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE notes SET category_id=?, title=?, content=? WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, note.getCategoryId());
            stmt.setString(2, note.getTitle());
            stmt.setString(3, note.getContent());
            stmt.setInt(4, note.getId());
            stmt.setInt(5, note.getUserId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE (move to trash)
    public void deleteNote(int userId, int noteId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE notes SET is_archived = 1 WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, noteId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PERMANENT DELETE
    public void forceDeleteNote(int userId, int noteId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM notes WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, noteId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ARCHIVE
    public void archiveNote(int userId, int noteId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE notes SET is_archived = 1 WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, noteId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RESTORE
    public void restoreNote(int userId, int noteId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE notes SET is_archived = 0 WHERE id=? AND user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, noteId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ARCHIVED NOTES
    public List<Note> getArchivedNotesByUserId(int userId) {
        List<Note> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM notes WHERE user_id = ? AND is_archived = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapNote(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Note mapNote(ResultSet rs) throws Exception {
        return new Note(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("category_id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getInt("is_archived"),
            rs.getTimestamp("created_at")
        );
    }
}
