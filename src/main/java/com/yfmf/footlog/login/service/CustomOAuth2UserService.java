package com.yfmf.footlog.login.service;

import com.yfmf.footlog.login.dto.KakaoResponse;
import com.yfmf.footlog.login.dto.OAuth2Response;
import com.yfmf.footlog.login.dto.CustomOAuth2User;
import com.yfmf.footlog.users.dto.UserOAuth2Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("OAuth2User: {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else { // 네이버 로그인 예정

            return null;
        }

        UserOAuth2Dto userDto = new UserOAuth2Dto();

        userDto.setUsername(oAuth2Response.getName());
        userDto.setSocialId(oAuth2Response.getProviderId());
        userDto.setRole("ROLE_USER");

        return new CustomOAuth2User(userDto);
    }
}
