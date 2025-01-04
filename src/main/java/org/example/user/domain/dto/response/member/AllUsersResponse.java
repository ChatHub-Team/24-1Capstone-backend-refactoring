package org.example.user.domain.dto.response.member;

import lombok.Getter;
import org.example.user.domain.entity.member.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
public class AllUsersResponse {

    private String id;

    @NotNull(message = "Username cannot be null")
    private String username;

    private String avatarUrl;

    private String followers_url;

    private String followings_url;

    public AllUsersResponse(User user) {
        this.id = String.valueOf(user.getId());
        this.username = user.getUsername();
        this.avatarUrl = user.getAvatarUrl();
        this.followers_url = user.getFollowersUrl();
        this.followings_url = user.getFollowingsUrl();
    }
}
