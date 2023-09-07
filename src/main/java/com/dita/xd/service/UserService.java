package com.dita.xd.service;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;

import java.util.Vector;

public interface UserService extends Service {
    boolean addTaggingUser(FeedBean feedBean, UserBean userBean);

    UserBean getUser(String userId);
    Vector<UserBean> search(String includedUserName);
}
