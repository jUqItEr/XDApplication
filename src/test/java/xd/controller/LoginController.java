package xd.controller;

import xd.model.UserBean;
import xd.repository.UserRepository;
import xd.service.implementation.LoginServiceImpl;

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
