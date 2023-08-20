package com.dita.xd.controller;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.LoginService;
import com.dita.xd.view.LoginFrame;

public class LoginController {

    private LoginService loginService;
    private UserRepository userRepository;

    public LoginController(LoginService loginService, UserRepository userRepository, LoginFrame loginFrame){
        this.userRepository = userRepository;
        this.loginService = loginService;
    }// -- End of cunstructor
    void login(String id, String pwd) {
        UserBean bean = loginService.login(id, pwd);

        if (bean != null){
            userRepository.setLogin(bean);
        } else{

        }// -- End of if
    }// -- End of function (login)
}// -- End of class
