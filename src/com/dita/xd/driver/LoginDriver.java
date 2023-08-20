package com.dita.xd.driver;

import com.dita.xd.service.implementation.LoginServiceImpl;

/**
 * @author     jUqItEr (Ki-seok Kang)
 * @deprecated For Testing
 * */
public class LoginDriver {
    public static void main(String[] args) {
        LoginServiceImpl svc = new LoginServiceImpl();
        String id = "aaa";
        String pwd = "1234";

        System.out.println(svc.login(id, pwd).getEmail());
    }
}
