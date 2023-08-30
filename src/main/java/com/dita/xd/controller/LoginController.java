package com.dita.xd.controller;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.LoginServiceImpl;
import com.dita.xd.service.implementation.UserServiceImpl;

public class LoginController {
    /* Fields declaration */
    private LoginServiceImpl loginSvc = null;
    private UserServiceImpl userSvc = null;

    private UserRepository repository = null;

    public LoginController() {
        loginSvc = new LoginServiceImpl();
        userSvc = new UserServiceImpl();
        repository = UserRepository.getInstance();
    }   // -- End of constructor

    public boolean login(String id, String pwd) {
        UserBean bean = loginSvc.login(id, pwd);

        if (bean != null) {
            repository.setLogin(bean);
        }
        return bean != null;
    }   // -- End of function (login)

    public boolean logout() {
        return loginSvc.logout(repository);
    }   // -- End of function (logout)

    public UserBean getUser(String id) {
        return userSvc.getUser(id);
    }

    public UserRepository getRepository() {
        return repository;
    }

    public boolean checkEmail(UserBean bean, String email) {
        return loginSvc.checkEmail(bean, email);
    }
}   // -- End of class
