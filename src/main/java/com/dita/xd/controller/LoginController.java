package com.dita.xd.controller;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.LoginServiceImpl;

public class LoginController {
    /* Fields declaration */
    private LoginServiceImpl svc = null;
    private UserRepository repository = null;

    public LoginController() {
        svc = new LoginServiceImpl();
        repository = UserRepository.getInstance();
    }   // -- End of constructor

    public boolean login(String id, String pwd) {
        UserBean bean = svc.login(id, pwd);

        if (bean != null) {
            repository.setLogin(bean);
        }
        return bean != null;
    }   // -- End of function (login)

    public boolean logout() {
        return svc.logout(repository);
    }   // -- End of function (logout)

    public UserRepository getRepository() {
        return repository;
    }
}   // -- End of class
