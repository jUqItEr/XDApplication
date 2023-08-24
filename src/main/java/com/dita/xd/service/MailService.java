package com.dita.xd.service;

public interface MailService extends Service {
    boolean appendCode(String email, String code);
    boolean checkValidation(String email, String code);
    boolean isValidEmail(String email);
    boolean revokeCode(String email);
    boolean sendRequestCode(String email);
    String generateCode();
}
