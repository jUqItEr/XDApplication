package com.dita.xd.driver;

import com.dita.xd.service.LoginService;

public class LoginDriver {
    public static void main(String[] args) {
        LoginService svc = new LoginService();
        String id = "aaa";
        String pwd = "1234";

        System.out.println(svc.login(id, pwd).getEmail());
    }
}
