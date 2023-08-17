package com.manta.Manta.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manta.Manta.user.model.User;
import com.manta.Manta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    public User create(String userId, String userName, String userEmail) {
    	User user = new User();
    	user.setUserId(userId);
    	user.setUserEmail(userEmail);
    	user.setUserName(userName);
    	userRepository.save(user);
        return user;
    }
    
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }
}