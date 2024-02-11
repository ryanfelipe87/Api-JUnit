package com.learning.api.services;

import com.learning.api.dtos.UserDto;
import com.learning.api.exceptions.DataIntegrityViolationException;
import com.learning.api.exceptions.ObjectNotFoundException;
import com.learning.api.models.User;
import com.learning.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Ryan";
    private static final String EMAIL = "rshow@example.com";
    private static final String PASSWORD = "7432";
    public static final int INDEX = 0;
    public static final String EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE = "Email exists in system, can't duplicate.";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDto userDto;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(userRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found with this ID: "));
        try{
            userService.findById(ID);
        }catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found with this ID: ", exception.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(user);

        User response = userService.create(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2L);
            userService.create(userDto);
        }catch (Exception exception){
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE, exception.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(user);

        User response = userService.update(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2L);
            userService.create(userDto);
        }catch (Exception exception){
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE, exception.getMessage());
        }
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}