package com.dita.xd.controller;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.LoginServiceImpl;

public class LoginController {
    private LoginServiceImpl svc = null;
    private UserRepository repository = null;

    public LoginController() {
        svc = new LoginServiceImpl();
        repository = UserRepository.getInstance();
    }

    boolean login(String id, String pwd) {
        UserBean bean = svc.login(id, pwd);

        if (bean != null) {
            repository.setLogin(bean);
        }
        return bean != null;
    }

    boolean logout() {
        return svc.logout(repository);
    }

    public UserRepository getRepository() {
        return repository;
    }
}
