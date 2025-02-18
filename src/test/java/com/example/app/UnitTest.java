package com.example.app;

import org.example.app.controller.UserController;
import org.example.app.model.User;
import org.example.app.service.UserService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Тестирование контроллера в изоляции. Для этого используется библиотека Mock
 */
public class UnitTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsersPage() throws Exception {
        User user1 = new User(1L, "Алиса", "alice@gmail.com");
        User user2 = new User(2L, "Анна", "anna@gmail.com");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
//                        .param("format", "page"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users_page"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testSaveUsers() throws Exception {
        User user = new User(1L, "Алиса", "alice@gmail.com");

        mockMvc.perform(post("/users").flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
        verify(userService).saveUser(user);
    }

    @Test
    public void testEditUser() throws Exception {
        User user = new User (1L, "Алиса", "alice@gmail.com");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attribute("user", user));

        verify(userService).getUserById(1L);
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).deleteUserById(1L);
    }
}
