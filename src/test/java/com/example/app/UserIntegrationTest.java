package com.example.app;

import org.example.app.Main;
import org.example.app.model.User;
import org.example.app.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Проверка работы контроллера в контексте работы Spring, включая все зависимости
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsersPage() throws Exception {
        List<User> users = Arrays.asList(new User(1L, "Алиса", "alice@gmail.com"),
                new User(2L, "Анна", "anna@gmail.com"));
        when(userService.getAllUsers()).thenReturn(users);

        // Выполнение запроса и проверка результата
        mockMvc.perform(get("/users").param("format", "page"))
                .andExpect(status().isOk())
                .andExpect(view().name("users_page"))
                .andExpect(model().attribute("users", users));

        verify(userService).getAllUsers();
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User(1L, "Алиса", "alice@gmail.com");

        mockMvc.perform(post("/").flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users_page"));

        verify(userService).saveUser(user);
    }

    @Test
    public void testEditUserForm() throws Exception {
        User user = new User(1L, "Алиса", "alice@gmail.com");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attribute("user", user));

        verify(userService).getUserById(1L);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User(1L, "Алиса", "alice@gmail.com");

        mockMvc.perform(post("/1").flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users_page"));

        verify(userService).saveUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users_page"));

        verify(userService).deleteUserById(1L);
    }
}
