package com.yfmf.footlog.login.service;

import com.yfmf.footlog.login.dto.KakaoResponse;
import com.yfmf.footlog.login.dto.NaverResponse;
import com.yfmf.footlog.login.dto.OAuth2Response;
import com.yfmf.footlog.login.dto.CustomOAuth2User;
import com.yfmf.footlog.users.dto.UserOAuth2Dto;
import com.yfmf.footlog.users.entity.User;
import com.yfmf.footlog.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("OAuth2User: {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        User foundUser = userRepository.findByUserName(oAuth2User.getName());
        UserOAuth2Dto userDto = new UserOAuth2Dto();

        if (foundUser == null) {

            userDto.setUsername(oAuth2Response.getName());
            userDto.setSocialId(oAuth2Response.getProviderId());
            User user = userDto.toEntity();

            userRepository.save(user);
        } else {

            userDto.setUsername(foundUser.getUserName());
            userDto.setSocialId(foundUser.getSocialId());
        }

        return new CustomOAuth2User(userDto);
    }
}
