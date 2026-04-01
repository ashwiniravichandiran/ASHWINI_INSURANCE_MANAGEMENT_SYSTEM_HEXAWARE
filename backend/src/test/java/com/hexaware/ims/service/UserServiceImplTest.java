package com.hexaware.ims.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ims.dto.UserRequestDTO;
import com.hexaware.ims.dto.UserResponseDTO;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    IUserService service;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @Test
    void testAddUser() {

        UserRequestDTO user = new UserRequestDTO(
                "Ashwini",
                "ashwini@gmail.com",
                "pass123",
                "ROLE_ADMIN",
                "Chennai",
                "123456789012",
                "ABCDE1234F",
                LocalDate.parse("2003-05-10")
        );

        UserResponseDTO savedUser = service.addUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testGetUserById() {

        UserResponseDTO user = service.getUserById(1);

        assertNotNull(user);
    }

//    @Test
//    void testLogin() {
//
//        UserResponseDTO user = service.login("ashwini@gmail.com", "pass123");
//
//        assertNotNull(user);
//    }

    @Test
    void testViewByRole() {

        List<UserResponseDTO> users = service.ViewByRole("admin");

        assertNotNull(users);
    }
}