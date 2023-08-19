package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;

public interface LoginServiceImpl extends ServiceImpl {
    UserBean login(String id, String pwd);
    boolean logout(UserRepository repository);
}
