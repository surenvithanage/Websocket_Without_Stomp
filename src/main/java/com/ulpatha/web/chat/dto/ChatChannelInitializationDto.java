package com.ulpatha.web.chat.dto;

public class ChatChannelInitializationDto {
	private long userIdOne;
	private long userIdTwo;
	private long postId;
	private String postTitle;

	public ChatChannelInitializationDto() {
	}

	public void setUserIdOne(long userIdOne) {
		this.userIdOne = userIdOne;
	}

	public void setUserIdTwo(long userIdTwo) {
		this.userIdTwo = userIdTwo;
	}

	public long getUserIdOne() {
		return this.userIdOne;
	}

	public long getUserIdTwo() {
		return userIdTwo;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
}
