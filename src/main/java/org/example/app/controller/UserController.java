package org.example.app.controller;

import org.example.app.aspect.TrackUserAction;
import org.example.app.model.User;
import org.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер
 */
@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageChannel requestChannel;

    /*
    Выборка всех пользователей
     */
    @GetMapping("/users")
    @TrackUserAction
    public String getAllUsersPage(Model model) {
        System.out.println("Get all users page called");
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users_page";
    }

    /**
     * Создание нового пользователя
     */
    @GetMapping("/users/new")
    @TrackUserAction
    public String createUserForm(Model model) {
        System.out.println("Create User Form called");
        model.addAttribute("user", new User());
        return "user_form";
    }
    /**
     * Сохранение пользователя
     */
    @PostMapping("/users")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        //Отправка в Spring Integration
        requestChannel.send(MessageBuilder.withPayload("New user saved " + user.toString()).build());
        return "redirect:/users";
    }

    /**
     * Изменение пользователя по id
     */
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user_form";
    }

    /**
     * Обновление пользователя по id
     */
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        //Отправка в Spring Integration
        requestChannel.send(MessageBuilder.withPayload("User saved " + + id + ": " + user.toString()).build());
        return "redirect:/users";
    }

    /**
     * Удаление пользователя по id
     */
      @DeleteMapping("/users/{id}")
      public String deleteUser(@PathVariable Long id) {
          userService.deleteUserById(id);
          //Отправка в Spring Integration
          requestChannel.send(MessageBuilder.withPayload("User deleted " + id).build());
          return "redirect:/users";
      }
}
