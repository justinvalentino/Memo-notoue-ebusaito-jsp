package memo.models;

import java.sql.Timestamp;

public class Category {
    private int id;
    private String name;
    private Integer usersId;          // keeps original backing field name for DB mapping
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int noteCount;

    public Category() {}

    // constructor used by DAOs
    public Category(int id, int usersId, String name) {
        this.id = id;
        this.usersId = usersId;
        this.name = name;
    }

    // convenience constructor without id
    public Category(int usersId, String name) {
        this.usersId = usersId;
        this.name = name;
    }

    // Keep original accessor names (for existing code), and also provide DAO-compatible names:
    // Original
    public Integer getUsersId() { return usersId; }
    public void setUsersId(Integer usersId) { this.usersId = usersId; }

    // Adapter methods (DAO-generated code expects getUserId()/setUserId())
    public int getUserId() { return usersId == null ? 0 : usersId; }
    public void setUserId(int userId) { this.usersId = userId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public int getNoteCount() { return noteCount; }
    public void setNoteCount(int noteCount) { this.noteCount = noteCount; }
}
