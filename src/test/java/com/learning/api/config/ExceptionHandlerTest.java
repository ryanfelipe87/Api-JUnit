package com.learning.api.config;

import com.learning.api.exceptions.DataIntegrityViolationException;
import com.learning.api.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerTest {

    public static final String OBJECT_NOT_FOUND_WITH_THIS_ID = "Object not found with this ID: ";
    public static final String EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE = "Email exists in system, can't duplicate.";
    @InjectMocks
    private ExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = handler
                .objectNotFound(
                        new ObjectNotFoundException(OBJECT_NOT_FOUND_WITH_THIS_ID),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJECT_NOT_FOUND_WITH_THIS_ID, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
    }

    @Test
    void dataIntegrityViolation() {
        ResponseEntity<StandardError> response = handler
                .dataIntegrityViolation(
                        new DataIntegrityViolationException(EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(EMAIL_EXISTS_IN_SYSTEM_CAN_T_DUPLICATE, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}