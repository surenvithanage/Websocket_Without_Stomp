package com.ulpatha.web.chat.dto;

public class EstablishedChatChannelDto {
  private String channelUuid;

  private String userOneFullName;

  private String userTwoFullName;

  private long postId;

  public EstablishedChatChannelDto() {}

  public EstablishedChatChannelDto(String channelUuid, String userOneFullName, String userTwoFullName, long postId) {
    this.channelUuid = channelUuid;
    this.userOneFullName = userOneFullName;
    this.userTwoFullName = userTwoFullName;
    this.postId = postId;
  }

  public void setChannelUuid(String channelUuid) {
    this.channelUuid = channelUuid;
  }

  public String getChannelUuid() {
    return this.channelUuid;
  }

  public void setUserOneFullName(String userOneFullName) {
    this.userOneFullName = userOneFullName;
  }

  public String getUserOneFullName() {
    return this.userOneFullName;
  }

  public void setUserTwoFullName(String userTwoFullName) {
    this.userTwoFullName = userTwoFullName;
  }

  public String getUserTwoFullName() {
    return this.userTwoFullName;
  }

  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }
}
