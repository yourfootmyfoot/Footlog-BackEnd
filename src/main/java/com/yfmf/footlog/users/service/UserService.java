package com.yfmf.footlog.users.service;

import com.yfmf.footlog.users.dto.UserSaveRequestDto;
import com.yfmf.footlog.users.dto.UserUpdateRequestDto;
import com.yfmf.footlog.users.entity.User;
import com.yfmf.footlog.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(UserSaveRequestDto requestDto) {

        return userRepository.save(requestDto.toEntity()).getUserId();
    }

    public Long update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다. id=" + id));

        user.update(requestDto);

        return id;
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다. id=" + id));

        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
