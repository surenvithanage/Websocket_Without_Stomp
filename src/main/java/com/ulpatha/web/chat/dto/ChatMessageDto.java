package com.ulpatha.web.chat.dto;

public class ChatMessageDto {
  private String contents;

  private long fromUserId;

  private long toUserId;

  private long postId;

  private String chatuuid;

  private String timesent;

  public ChatMessageDto(){}

  public ChatMessageDto(String contents, long fromUserId, long toUserId, long postId, String chatuuid, String timesent) {
    this.contents = contents;
    this.fromUserId = fromUserId;
    this.toUserId = toUserId;
    this.postId = postId;
    this.timesent = timesent;
    this.chatuuid = chatuuid;
  }

  public String getContents() {
    return this.contents;
  }

  public void setToUserId(long toUserId) {
    this.toUserId = toUserId;
  }

  public long getToUserId() {
    return this.toUserId;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public void setFromUserId(long userId) {
    this.fromUserId = userId;
  }

  public long getFromUserId() {
    return this.fromUserId;
  }

  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }

  public String getChatuuid() {
    return chatuuid;
  }

  public void setChatuuid(String chatuuid) {
    this.chatuuid = chatuuid;
  }

  public String getTimesent() {
    return timesent;
  }

  public void setTimesent(String timesent) {
    this.timesent = timesent;
  }
}
