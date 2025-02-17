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

    @GetMapping(value = "/users", params = "format=page")
    @TrackUserAction
    public String getAllUsersPage(Model model)
    {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users_page";
    }

    @RequestMapping(name = "/new", params = "format=page")
    @TrackUserAction
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users_page";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_form";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users_page";
    }

  //  @GetMapping(name = "/{id}/delete", params = "format=page")
    @TrackUserAction
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users_page";
    }
}
