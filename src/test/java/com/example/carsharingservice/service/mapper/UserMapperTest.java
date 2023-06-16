package com.example.carsharingservice.service.mapper;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import org.junit.jupiter.api.Test;

class UserMapperTest {
    private static final long USER_ID = 1L;
    private static final long CHAT_ID = 1L;
    private static final Role ROLE = Role.CUSTOMER;
    private static final String EMAIL = "qwerty123@mail.com";
    private static final String PASSWORD = "qwerty123";
    private static final String LASTNAME = "Alice";
    private static final String FIRSTNAME = "Bobson";
    private static final String TEST_MESSAGE = "Expected true, but was false: ";
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper = new UserMapper();
    private final User user = getUser();
    private final UserRequestDto userRequestDto = getUserRequestDto();

    @Test
    void testToDto_ok() {
        UserResponseDto mapToDto = userMapper.mapToDto(user);

        assertEquals(TEST_MESSAGE,
                USER_ID, mapToDto.getId());
        assertEquals(TEST_MESSAGE,
                ROLE, mapToDto.getRole());
        assertEquals(TEST_MESSAGE,
                EMAIL, mapToDto.getEmail());
        assertEquals(TEST_MESSAGE,
                FIRSTNAME, mapToDto.getFirstName());
        assertEquals(TEST_MESSAGE,
                LASTNAME, mapToDto.getLastName());
    }

    @Test
    void testToModel_ok() {
        User mapToModel = userMapper.mapToModel(userRequestDto);

        assertEquals(TEST_MESSAGE,
                ROLE, mapToModel.getRole());
        assertEquals(TEST_MESSAGE,
                EMAIL, mapToModel.getEmail());
        assertEquals(TEST_MESSAGE,
                FIRSTNAME, mapToModel.getFirstName());
        assertEquals(TEST_MESSAGE,
                LASTNAME, mapToModel.getLastName());
    }

    private static UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail(EMAIL);
        userRequestDto.setPassword(PASSWORD);
        userRequestDto.setFirstName(FIRSTNAME);
        userRequestDto.setLastName(LASTNAME);
        userRequestDto.setRole(ROLE);
        return userRequestDto;
    }

    private static User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setLastName(LASTNAME);
        user.setFirstName(FIRSTNAME);
        user.setChatId(CHAT_ID);
        user.setRole(ROLE);
        return user;
    }
}
