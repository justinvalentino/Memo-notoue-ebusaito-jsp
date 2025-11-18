package main.java.memo.models;

import java.sql.Timestamp;

public class Note {
    private int id;
    private String title;
    private String content;
    private boolean isArchived;
    private boolean isDeleted;
    private int usersId;       // FK ke users
    private Integer categoriesId; // nullable FK ke categories
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Note() {}

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

    public int getUsersId() { return usersId; }
    public void setUsersId(int usersId) { this.usersId = usersId; }

    public Integer getCategoriesId() { return categoriesId; }
    public void setCategoriesId(Integer categoriesId) { this.categoriesId = categoriesId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
