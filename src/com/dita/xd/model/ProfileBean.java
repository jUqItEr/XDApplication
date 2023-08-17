package com.dita.xd.model;

import java.time.LocalDateTime;
import java.sql.Date;

public class ProfileBean {

    private String userTblId;
    private String nickname;
    private String profileImage;
    private String headerImage;
    private String address;
    private char gender;
    private String website;
    private Date birthday;
    private LocalDateTime joinAt;

    public ProfileBean(String userTblId, String nickname, String profileImage, String headerImage, String address,
            char gender, String website, Date birthday, LocalDateTime joinAt) {
        this.userTblId = userTblId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.headerImage = headerImage;
        this.address = address;
        this.gender = gender;
        this.website = website;
        this.birthday = birthday;
        this.joinAt = joinAt;
    }

    public String getUserTblId() {
        return userTblId;
    }

    public void setUserTblId(String userTblId) {
        this.userTblId = userTblId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(LocalDateTime joinAt) {
        this.joinAt = joinAt;
    }
}