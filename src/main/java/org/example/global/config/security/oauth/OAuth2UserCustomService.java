package org.example.global.config.security.oauth;
import lombok.RequiredArgsConstructor;
import org.example.user.domain.entity.member.User;
import org.example.user.repository.member.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환
        saveOrUpdate(user, userRequest);
        return user; //사용자 객체는 식별자, 이름, 이메일, 프로필 사진 링크 등의 정보를 담고 있다
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
//        System.out.println("getAttributes:" + oAuth2User.getAttributes());
        OAuth2UserInfo oAuth2UserInfo = new GithubUserInfo(oAuth2User.getAttributes());

        String username = oAuth2UserInfo.getUserName();
        String password = passwordEncoder.encode("겟인데어");

        User user = userRepository.findByUsername(username)
                .map(entity -> entity.update(username))
                .orElse(User.builder()
                        .username(username)
                        .avatarUrl(oAuth2UserInfo.getAvatarUrl())
                        .followersUrl(oAuth2UserInfo.getFollowersUrl())
                        .followingsUrl(oAuth2UserInfo.getFollowingUrl())
                        .password(password)
                        .provider(oAuth2UserInfo.getProvider())
                        .providerId(oAuth2UserInfo.getProviderId())
                        .build());

        user.setAccessToken(String.valueOf(userRequest.getAccessToken().getTokenValue()));

        return userRepository.save(user);
    }

}




