package com.example.firebase.user.repository;

import com.example.firebase.user.domain.User;
import com.google.cloud.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .name("Aaron222")
                .email("aaron@gmail.com")
                .create_dt(Timestamp.now())
                .build();

        userRepository.editUser(user);
    }

    @Test
    void fail_editUser() throws Exception {
        User user = User.builder()
                .id("yXMhDPpulEWGqO3ERzEU")
                .name("Aaron")
                .email("abcdefg@gmail.com")
                .create_dt(Timestamp.now())
                .build();

        assertThatThrownBy(() -> userRepository.editUser(user))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void findUserByEmail() throws Exception {
        String email = "park@gmail.com";

        Optional<User> userOptional = userRepository.findUserByEmail(email);
        User user = userOptional.get();

        assertEquals("Park", user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    void fail_findUser() throws Exception {
        String email = "abcdefg@gmail.com";
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        Assertions.assertTrue(userOptional.isEmpty());
    }

    @Test
    void removeUserByEmail() throws Exception {
        String email = "bbb@gmail.com";
        userRepository.removeUserByEmail(email);
    }

    @Test
    void fail_removeUserByEmail() throws Exception {
        String email = "abcdefg@gmail.com";

        assertThatThrownBy(() -> userRepository.removeUserByEmail(email))
                .isInstanceOf(RuntimeException.class);
    }
}