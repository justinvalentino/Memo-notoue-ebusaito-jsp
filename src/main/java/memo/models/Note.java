package memo.models;

import java.sql.Timestamp;

public class Note {
    private int id;
    private String title;
    private String content;
    private boolean isArchived;
    private boolean isDeleted;
    private int usersId;             // FK ke users
    private Integer categoriesId;    // nullable FK ke categories
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Note() {}

    // Constructor used by DAO mapping (matches NoteDAO.mapNote)
    public Note(int id, int usersId, Integer categoriesId, String title, String content, int isArchivedInt, Timestamp createdAt) {
        this.id = id;
        this.usersId = usersId;
        this.categoriesId = categoriesId;
        this.title = title;
        this.content = content;
        this.isArchived = (isArchivedInt != 0);
        this.createdAt = createdAt;
    }

    // Basic getters/setters (keep existing names) plus adapter methods expected by DAO
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isArchived() { return isArchived; }
    public void setArchived(boolean archived) { isArchived = archived; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    // keep original names
    public int getUsersId() { return usersId; }
    public void setUsersId(int usersId) { this.usersId = usersId; }

    public Integer getCategoriesId() { return categoriesId; }
    public void setCategoriesId(Integer categoriesId) { this.categoriesId = categoriesId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // Adapter methods expected by the DAOs I generated:
    public int getUserId() { return usersId; }            // DAOs use getUserId()
    public Integer getCategoryId() { return categoriesId; } // DAOs use getCategoryId()
    public void setUserId(int userId) { this.usersId = userId; }
    public void setCategoryId(Integer categoryId) { this.categoriesId = categoryId; }
}
