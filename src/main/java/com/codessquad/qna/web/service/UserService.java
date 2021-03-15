package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(User user) {
        validateUserID(user);
        userRepository.save(user);
    }

    private void validateUserID(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(x -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("찾는 user가 없습니다"));
    }

    public void updateUser(long id, String testPassword, User user) {
        User originUser = findUser(id);
        validatePassword(originUser, testPassword);
        originUser.update(user);
        userRepository.save(originUser);
    }

    private void validatePassword(User originUser, String testPassword) {
        if (!originUser.isMatchingPassword(testPassword)) {
            throw new IllegalStateException("잘못된 비밀번호 입니다");
        }
    }
}
