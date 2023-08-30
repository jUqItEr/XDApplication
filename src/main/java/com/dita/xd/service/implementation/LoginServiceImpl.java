package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.LoginService;

import java.sql.*;

public class LoginServiceImpl implements LoginService {
    DBConnectionServiceImpl pool = null;
    PasswordHashServiceImpl hashSvc = null;
    UserServiceImpl userSvc = null;

    public LoginServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
        hashSvc = new PasswordHashServiceImpl();
        userSvc = new UserServiceImpl();
    }

    @Override
    public boolean checkEmail(UserBean bean, String email) {
        return bean != null && bean.getEmail().equals(email);
    }

    @Override
    public UserBean login(String id, String pwd) {
        UserBean bean = userSvc.getUser(id);

        if (bean != null) {
            String beanPwd = bean.getPassword();

            if (!hashSvc.isValidPassword(pwd, beanPwd)) {
                bean = null;
            }
        }
        return bean;
    }

    @Override
    public boolean logout(UserRepository repository) {
        repository.setLogout();
        return !repository.hasLogin();
    }
}
