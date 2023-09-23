package com.example.firebase.user.Service;

import com.example.firebase.user.domain.User;
import com.example.firebase.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        try {
            return userRepository.getUsers();
        } catch (Exception e) {
            log.error("UserRepository getUsers exception");
            return Collections.EMPTY_LIST;
        }
    }
}
