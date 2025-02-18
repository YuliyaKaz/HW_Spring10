package org.example.app.controller;

import org.example.app.aspect.TrackUserAction;
import org.example.app.model.User;
import org.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @TrackUserAction
    public String getAllUsersPage(Model model) {
        System.out.println("Get all users page called");
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users_page";
    }

    @GetMapping("/users/new")
    @TrackUserAction
    public String createUserForm(Model model) {
        System.out.println("Create User Form called");
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user_form";
    }
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users";
    }
      @DeleteMapping("/users/{id}")
      public String deleteUser(@PathVariable Long id) {
          userService.deleteUserById(id);
          return "redirect:/users";
      }
}
