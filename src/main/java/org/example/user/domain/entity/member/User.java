package org.example.user.domain.entity.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.example.global.common.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 사용자 이름, 필수값, 유니크 제약
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "followers_url")
    private String followersUrl;

    @Column(name = "followings_url")
    private String followingsUrl;

    @Column(nullable = false)
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "access_token")
    private String accessToken;

    @Builder
    public User(String username, String avatarUrl, String password, String followersUrl, String followingsUrl, String provider, String providerId, String accessToken) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.followersUrl = followersUrl;
        this.followingsUrl = followingsUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.accessToken = accessToken;
    }

    // 사용자 이름 변경
    public User update(String username) {
        this.username = username;
        return this;
    }

    public User addAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // true -> 만료되지 않았음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // true -> 잠금되지 않았음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true -> 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // true -> 사용 가능
    }
}

