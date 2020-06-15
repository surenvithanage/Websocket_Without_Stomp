package com.ulpatha.web.chat.service.user.impl;

import com.ulpatha.web.chat.dto.NotificationDTO;
import com.ulpatha.web.chat.model.User;
import com.ulpatha.web.chat.repository.UserRepository;
import com.ulpatha.web.chat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public User getUser(long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public void setIsPresent(User user, Boolean stat) {
        user.setPresent(stat);
        userRepository.save(user);
    }

    public Boolean isPresent(User user) {
        return user.getPresent();
    }

//    public void notifyUser(User recipientUser, NotificationDTO notification) {
//        if (this.isPresent(recipientUser)) {
//            simpMessagingTemplate
//                    .convertAndSend("/topic/user.notification." + recipientUser.getUserId(), notification);
//        } else {
//            System.out.println("sending email notification to " + recipientUser.getName());
//            // TODO: send email
//        }
//    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
