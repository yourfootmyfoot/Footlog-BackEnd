package com.yfmf.footlog.domain.user.service;

import com.yfmf.footlog.domain.user.dto.UserSaveRequestDto;
import com.yfmf.footlog.domain.user.dto.UserUpdateRequestDto;
import com.yfmf.footlog.domain.user.entity.User;
import com.yfmf.footlog.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(UserSaveRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();  // getUserId() -> getId()
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

    //테스트 코드용 기능입니다. 실제 Read 작업은 별개로 진행합니다.
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다. id=" + id));
    }
}
