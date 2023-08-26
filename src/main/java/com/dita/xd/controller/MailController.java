package com.dita.xd.controller;

import com.dita.xd.service.implementation.MailServiceImpl;

public class MailController {
    private MailServiceImpl svc = null;

    public MailController() {
        svc = new MailServiceImpl();
    }   // -- End of constructor

    public boolean checkRequestCode(String email, String code) {
        return svc.checkValidation(email, code);
    }   // -- End of function (checkRequestCode)

    public boolean sendRequestCode(String email) {
        return svc.sendRequestCode(email);
    }   // -- End of function (sendRequestCode)

    public boolean isValidEmail(String email) {
        return svc.isValidEmail(email);
    }

    public boolean revokeCode(String email) {
        return svc.revokeCode(email);
    }
}
