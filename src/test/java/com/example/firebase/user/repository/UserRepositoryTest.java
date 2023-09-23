package com.example.firebase.user.repository;

import com.example.firebase.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void method() throws Exception {
        List<User> users = userRepository.getUsers();
        users.forEach(user -> System.out.println("user = " + user));
    }

}