package com.dita.xd.controller;

import com.dita.xd.service.implementation.RegisterServiceImpl;

public class RegisterController {
    private RegisterServiceImpl svc = null;

    public RegisterController(){
        svc = new RegisterServiceImpl();
    }

    public boolean changePassword(String id, String pwd) {
        return svc.changePassword(id, pwd);
    }

    public boolean register(String id, String pwd, String email){
        return svc.register(id, pwd, email);
    }
    public boolean hasId(String id){
        return svc.hasId(id);
    }

    public boolean hasEmail(String email){
        return svc.hasEmail(email);
    }
}