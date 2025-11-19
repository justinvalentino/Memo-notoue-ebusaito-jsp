package main.java.memo.models;

import java.sql.Timestamp;

public class Category {
    private int id;
    private String name;
    private Integer usersId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    private int noteCount;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getUsersId() {
        return usersId;
    }
    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getNoteCount() {
        return noteCount;
    }
    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
}