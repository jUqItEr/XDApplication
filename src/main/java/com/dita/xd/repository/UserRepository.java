package com.dita.xd.repository;

import com.dita.xd.model.UserBean;

public class UserRepository {
    /* Singleton Class */
    private static volatile UserRepository instance = null;

    private UserBean userAccount;

    public UserRepository() {

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    return new UserRepository();
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
}
