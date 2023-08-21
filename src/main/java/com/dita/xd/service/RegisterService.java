package com.dita.xd.service;

public interface RegisterService extends Service {
    boolean register(String id, String pwd, String email, String nickname);
}
