package com.ye.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ye.task.dto.CommentDto;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id ;

    private String content ;

    private Date createdAt ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false )
    @JoinColumn( name = "user_id" , nullable = false )
    @OnDelete(action = OnDeleteAction.CASCADE )
    @JsonIgnore
    private User user;


    @ManyToOne(fetch = FetchType.LAZY , optional = false )
    @JoinColumn( name = "task_id" , nullable = false )
    @OnDelete(action = OnDeleteAction.CASCADE )
    @JsonIgnore
    private Task task ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public CommentDto getCommentDto(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(getId());
        commentDto.setContent(getContent());
        commentDto.setCreatedAt(getCreatedAt());
        commentDto.setUserId(getUser().getId());
        commentDto.setTaskId(getTask().getId());
        return commentDto;
    }
}
