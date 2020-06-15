package com.ulpatha.web.chat.service.user;

import com.ulpatha.web.chat.dto.NotificationDTO;
import com.ulpatha.web.chat.model.User;

public interface UserService {
    User getUser(String userEmail);
    User getUser(long userId);
    void setIsPresent(User user, Boolean stat);
//    void notifyUser(User recipientUser, NotificationDTO notification);
    User saveUser(User user);
}
