package xd.repository;

import xd.model.ProfileBean;
import xd.model.UserBean;

public class UserRepository {
    /* Singleton Class */
    private static volatile UserRepository instance = null;

    private UserBean userAccount;
    private ProfileBean userProfile;

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

    public ProfileBean getUserProfile() {
        return userProfile;
    }

    public void setLogin(UserBean bean) {
        this.userAccount = bean;
    }

    public void setLogout() {
        this.userAccount = null;
    }

    public void setUserProfile(ProfileBean bean) {
        this.userProfile = bean;
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
