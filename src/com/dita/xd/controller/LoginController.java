package com.dita.xd.controller;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.LoginService;

public class LoginController {
    private LoginService svc = null;
    private UserRepository repository = null;

    public LoginController() {
        svc = new LoginService();
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
}
