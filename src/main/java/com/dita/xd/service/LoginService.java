package com.dita.xd.service;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;

public interface LoginService extends Service {
    UserBean login(String id, String pwd);
    boolean logout(UserRepository repository);
}
