package pl.mskreczko.api.user.web;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mskreczko.api.domain.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.service.UserAuthService;
import pl.mskreczko.api.domain.user.web.UserAuthController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAuthController.class)
public class UserAuthControllerTest {

    @MockBean
    UserAuthService userAuthService;
    @Autowired
    MockMvc mvc;

    @Test
    void register_returnsCreated() throws Exception {
        Mockito.doNothing().when(userAuthService).registerUser(
                new UserRegistrationDto("test@test.com", "John", "Doe", "ZAQ!2wsx")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@test.com",
                            "firstName": "John",
                            "lastName": "Doe",
                            "password": "ZAQ!2wsx"
                        }
                        """)).andExpect(status().isCreated());
    }

    @Test
    void register_returnsConflict() throws Exception {
        Mockito.doThrow(EntityAlreadyExistsException.class).when(userAuthService).registerUser(
                new UserRegistrationDto("test@test.com", "John", "Doe", "ZAQ!2wsx")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@test.com",
                            "firstName": "John",
                            "lastName": "Doe",
                            "password": "ZAQ!2wsx"
                        }
                        """)).andExpect(status().isConflict());
    }

    @Test
    void login_returnsOk() throws Exception {
        Mockito.doReturn("token").when(userAuthService).loginUser(
                new UserLoginDto("test@test.com", "ZAQ!2wsx")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@test.com",
                            "password": "ZAQ!2wsx"
                        }
                        """)).andExpect(status().isOk()).andExpect(
                                result -> Assertions.assertEquals("token", result.getResponse().getContentAsString()));
    }

    @Test
    void login_returnsUnauthorized() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(userAuthService).loginUser(
                new UserLoginDto("test@test.com", "ZAQ!2wsx")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@test.com",
                            "password": "ZAQ!2wsx"
                        }
                        """)).andExpect(status().isUnauthorized());
    }
}
