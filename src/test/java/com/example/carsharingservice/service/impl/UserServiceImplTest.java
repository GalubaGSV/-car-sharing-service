package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private List<User> users = new ArrayList<>();
    private static final int LEGAL_USER_INDEX = 0;
    private static final int ILLEGAL_USER_INDEX = 1;
    private static final long LEGAL_USER_ID = 1L;
    private static final long ILLEGAL_USER_ID = 0L;
    private static final long LEGAL_CHAT_ID = 1L;
    private static final long ILLEGAL_CHAT_ID = 0L;
    private static final String LEGAL_FIRSTNAME = "Bob";
    private static final String LEGAL_LASTNAME = "Bobson";
    private static final String LEGAL_EMAIL = "bob@mail.com";
    private static final String ILLEGAL_EMAIL = "bob";
    private static final String LEGAL_PASSWORD = "qwerty123";
    private static final String ILLEGAL_PASSWORD = "qwa";
    private static final Role LEGAL_ROLE = Role.CUSTOMER;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User firstUser = getLegalUser();
        User secondUser = getIllegalUser();
        users = List.of(firstUser, secondUser);
    }

    @Test
    void testUserGet_ok() {
        given(userRepository.getReferenceById(LEGAL_USER_ID))
                .willReturn(users.get(LEGAL_USER_INDEX));
        User user = userService.get(LEGAL_USER_ID);

        assertEquals(users.get(LEGAL_USER_INDEX), user);
    }

    @Test
    void testUserGet_notOk() {
        given(userRepository.getReferenceById(ILLEGAL_USER_ID)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userService.get(ILLEGAL_USER_ID));
    }

    @Test
    void testUserFindByEmail_ok() {
        given(userRepository.findByEmail(LEGAL_EMAIL))
                .willReturn(Optional.ofNullable(users.get(LEGAL_USER_INDEX)));
        User user = userService.findByEmail(LEGAL_EMAIL).orElseThrow();

        assertEquals(users.get(LEGAL_USER_INDEX), user);
    }

    @Test
    void testUserFindByEmail_notOk() {
        given(userRepository.findByEmail(ILLEGAL_EMAIL))
                .willThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> userService.findByEmail(ILLEGAL_EMAIL));
    }

    @Test
    void testUserSave_ok() {
        given(userRepository.save(users.get(LEGAL_USER_INDEX)))
                .willReturn(users.get(LEGAL_USER_INDEX));
        User user = userService.save(users.get(LEGAL_USER_INDEX));

        assertEquals(users.get(LEGAL_USER_INDEX), user);
    }

    @Test
    void testUserSave_notOk() {
        given(userRepository.save(users.get(ILLEGAL_USER_INDEX)))
                .willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userService.save(users.get(ILLEGAL_USER_INDEX)));
    }

    @Test
    void testUserFindByChatId_ok() {
        given(userRepository.findByChatId(LEGAL_CHAT_ID))
                .willReturn(Optional.ofNullable(users.get(LEGAL_USER_INDEX)));
        User user = userService.findByChatId(LEGAL_CHAT_ID).orElseThrow();

        assertEquals(users.get(LEGAL_USER_INDEX), user);
    }

    @Test
    void testUserFindAllByRole_ok() {
        User userOne = getLegalUser();
        User userTwo = getLegalUser();
        userTwo.setId(LEGAL_USER_ID + 1L);
        User userThree = getLegalUser();
        userThree.setId(LEGAL_USER_ID + 2L);
        userThree.setRole(Role.MANAGER);

        given(userRepository.findAllByRole(LEGAL_ROLE))
                .willReturn(List.of(userOne, userTwo));

        List<User> userList = userService.findAllByRole(LEGAL_ROLE);

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    @Test
    void testUserFindAllWithChatId_ok() {
        User userOne = getLegalUser();
        User userTwo = getLegalUser();
        userTwo.setId(LEGAL_USER_ID + 1L);
        User userThree = getLegalUser();
        userThree.setId(LEGAL_USER_ID + 2L);
        userThree.setChatId(null);

        given(userRepository.findAllWithChatId()).willReturn(List.of(userOne, userTwo));

        List<User> userList = userService.findAllWithChatId();

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    @Test
    void testUserUpdate_ok() {
        User notUpdatedUser = getLegalUser();

        User updatedUser = getLegalUser();
        updatedUser.setLastName("Alison");
        updatedUser.setFirstName("Alice");

        given(userRepository.findById(notUpdatedUser.getId()))
                .willReturn(Optional.of(notUpdatedUser));
        given(userRepository.save(any(User.class)))
                .willReturn(updatedUser);

        User result = userService.update(notUpdatedUser);

        assertEquals(updatedUser, result);
    }

    @Test
    void testUserUpdate_notOk() {
        User notUpdatedUser = getLegalUser();
        User updatedUser = getLegalUser();
        updatedUser.setLastName("Alison");
        updatedUser.setFirstName("Alice");

        given(userRepository.findById(notUpdatedUser.getId()))
                .willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.update(notUpdatedUser));
    }

    private User getLegalUser() {
        User user = new User();
        user.setId(LEGAL_USER_ID);
        user.setLastName(LEGAL_LASTNAME);
        user.setFirstName(LEGAL_FIRSTNAME);
        user.setChatId(LEGAL_CHAT_ID);
        user.setEmail(LEGAL_EMAIL);
        user.setPassword(LEGAL_PASSWORD);
        user.setRole(LEGAL_ROLE);
        return user;
    }

    private User getIllegalUser() {
        User user = new User();
        user.setId(ILLEGAL_USER_ID);
        user.setLastName(LEGAL_LASTNAME);
        user.setFirstName(LEGAL_FIRSTNAME);
        user.setChatId(ILLEGAL_CHAT_ID);
        user.setEmail(ILLEGAL_EMAIL);
        user.setPassword(ILLEGAL_PASSWORD);
        return user;
    }
}
