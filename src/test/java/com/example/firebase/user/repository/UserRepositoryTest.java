package com.example.firebase.user.repository;

import com.example.firebase.user.domain.User;
import com.google.cloud.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers() throws Exception {
        List<User> users = userRepository.getAllUsers();
        users.forEach(user -> System.out.println("user = " + user));
    }

    @Test
    void addUser() throws Exception {
        User user = User.builder()
                .name("bbb")
                .email("bbb@gmail.com")
                .create_dt(Timestamp.now())
                .build();

        userRepository.addUser(user);
    }

    @Test
    void fail_addUser() throws Exception {
        User user = User.builder()
                .name("Choi")
                .email("choi@gmail.com")
                .create_dt(Timestamp.now())
                .build();

        assertThatThrownBy(() -> userRepository.addUser(user))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void editUser() throws Exception {
        User user = User.builder()
                .id("yXMhDPpulEWGqO3ERzEU")
                .name("Aaron1")
                .email("aaron@gmail.com")
                .create_dt(Timestamp.now())
                .build();

        userRepository.editUser(user);
    }
}