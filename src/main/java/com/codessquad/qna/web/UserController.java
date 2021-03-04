package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String createUser(User user) {
        System.out.println("유저생성!");
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        //id 중복 확인, null일경우 처리
        User foundUser = fingByUserId(userId);
        model.addAttribute("user", foundUser);
        return "/user/profile";
    }

    private User fingByUserId(String userId) {
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
            }
        }
        return foundUser;
    }

    @GetMapping("/users/{userId}/form")
    public String getUpdateForm(@PathVariable String userId, Model model){
        User foundUser = fingByUserId(userId);
        model.addAttribute("user", foundUser);
        return "/user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUser(User updatedUser){
        System.out.println(users.size());
        System.out.println('H');
        User foundUser = fingByUserId(updatedUser.getUserId());
        foundUser.setPassword(updatedUser.getPassword());
        foundUser.setName(updatedUser.getName());
        foundUser.setEmail(updatedUser.getEmail());
        return  "redirect:/users";
    }

}
