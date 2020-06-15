package com.ulpatha.web.chat.dto;

public class ChatResponseDto {
    private String contents;

    private long fromUserId;

    private long toUserId;

    private String receivedDate;

    private String chatuuid;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getChatuuid() {
        return chatuuid;
    }

    public void setChatuuid(String chatuuid) {
        this.chatuuid = chatuuid;
    }
}
