package com.dita.xd.repository;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.model.UserBean;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import java.text.SimpleDateFormat;

public class UserRepository {
    /* Singleton Class */
    private static volatile UserRepository instance = null;

    private final ActivityController controller;

    private UserBean userAccount;
    private UserBean buildProfile;

    private UserRepository() {
        controller = new ActivityController();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public UserBean getUserAccount() {
        return userAccount;
    }

    public void setLogin(UserBean bean) {
        this.userAccount = bean;
    }

    public void setLogout() {
        this.userAccount = null;
    }

    public boolean hasLogin() {
        return userAccount != null;
    }

    public String getUserId() {
        String id = null;

        if (hasLogin()) {
            id = userAccount.getUserId();
        }
        return id;
    }

    public String getUserEmail() {
        String email = null;

        if (hasLogin()) {
            email = userAccount.getEmail();
        }
        return email;
    }

    public String getUserNickname() {
        String nickname = null;

        if (hasLogin()) {
            nickname = userAccount.getNickname();
        }
        return nickname;
    }

    public String getCreatedAt() {
        String createdAt = null;

        if (hasLogin()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM");
            createdAt = sdf.format(userAccount.getCreatedAt());
        }
        return createdAt;
    }

    public void updateProfile(UserBean newProfile) {
        if (buildProfile == null) {
            buildProfile = new UserBean();
        }
        if (newProfile.getNickname() != null && !newProfile.getNickname().isEmpty()) {
            buildProfile.setNickname(newProfile.getNickname());
        }
        if (newProfile.getProfileImage() != null) {
            buildProfile.setProfileImage(newProfile.getProfileImage());
        }
        if (newProfile.getHeaderImage() != null) {
            buildProfile.setHeaderImage(newProfile.getHeaderImage());
        }
        if (newProfile.getBirthday() != null) {
            buildProfile.setBirthday(newProfile.getBirthday());
        }
        if (newProfile.getAddress() != null && !newProfile.getAddress().isEmpty()) {
            buildProfile.setAddress(newProfile.getAddress());
        }
        if (newProfile.getGender() != null) {
            buildProfile.setGender(newProfile.getGender());
        }
        if (newProfile.getIntroduce() != null && !newProfile.getIntroduce().isEmpty()) {
            buildProfile.setIntroduce(newProfile.getIntroduce());
        }
        if (newProfile.getWebsite() != null && !newProfile.getWebsite().isEmpty()) {
            buildProfile.setWebsite(newProfile.getWebsite());
        }
    }

    public boolean verifyProfile() {
        boolean result = true;

        if (buildProfile.getNickname() == null || buildProfile.getNickname().isEmpty()) {
            result = false;
        } else {
            userAccount.setNickname(buildProfile.getNickname());
            userAccount.setProfileImage(buildProfile.getProfileImage());
            userAccount.setAddress(buildProfile.getAddress());
            userAccount.setIntroduce(buildProfile.getIntroduce());
            userAccount.setHeaderImage(buildProfile.getHeaderImage());
            userAccount.setGender(buildProfile.getGender());
            userAccount.setWebsite(buildProfile.getWebsite());
        }
        if (result) {
            controller.setProfile(userAccount);
        }
        buildProfile = null;
        return result;
    }
}
