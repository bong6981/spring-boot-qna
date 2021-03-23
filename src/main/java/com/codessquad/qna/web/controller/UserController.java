package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.login(userId, password);
        HttpSessionUtils.setUser(session, user);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        HttpSessionUtils.removeUser(session);
        return "redirect:/";
    }

    @PostMapping
    public String createUser(User user, Model model) {
        if (userService.checkDuplicateID(user)) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다");
            return "/user/updateFormWithError";
        }
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        checkLogin(session);
        model.addAttribute("user", validateAndGetUser(id, session));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, String testPassword, User user, HttpSession session, Model model) {
        checkLogin(session);

        User loginUser = validateAndGetUser(id, session);
        if(!userService.isMatchingPassword(loginUser, testPassword)) {
            model.addAttribute("errorMessage", "비밀번호가 틀렸습니다");
            return "/user/formWithError";
        }

        userService.updateUser(testPassword, loginUser, user);
        return "redirect:/users";
    }

    private void checkLogin(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoginException();
        }
    }

    private User validateAndGetUser(long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionedUser(session);
        if (!loginUser.isSameId(id)) {
            throw new IllegalAccessException();
        }
        return loginUser;
    }
}
