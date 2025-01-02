package org.example.user.domain.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubProfileResponse {
    private Long id;
    private String password;
    private String followersUrl;
    private String provider;
    private String providerId;
    private String userName;
    private String avatarUrl;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private boolean hireable;
    private String bio;
    private String twitterUsername;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private String createdAt;
    private String updatedAt;
    private int privateGists;
    private int totalPrivateRepos;
    private int ownedPrivateRepos;
    private int diskUsage;
    private int collaborators;
    private boolean twoFactorAuthentication;
    private String url;
    private String htmlUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private boolean siteAdmin;
}
