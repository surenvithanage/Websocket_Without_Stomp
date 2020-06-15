package com.ulpatha.web.chat.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chatChannel")
public class ChatChannel {

    @Id
    @NotNull
    private String uuid;

    @OneToOne
    @JoinColumn(name = "userIdOne")
    private User userOne;

    @OneToOne
    @JoinColumn(name = "userIdTwo")
    private User userTwo;

    @NotNull
    private long postId;

    private String postTitle;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public ChatChannel(User userOne, User userTwo, long postId, String postTitle) {
        this.uuid = UUID.randomUUID().toString();
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.postId = postId;
        this.postTitle = postTitle;
    }

    public ChatChannel() {
    }

    public void setUserTwo(User user) {
        this.userTwo = user;
    }

    public void setUserOne(User user) {
        this.userOne = user;
    }

    public User getUserOne() {
        return this.userOne;
    }

    public User getUserTwo() {
        return this.userTwo;
    }

    public String getUuid() {
        return this.uuid;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
