package com.dita.xd.service;

public interface RegisterService extends Service {
    boolean hasEmail(String email);
    boolean hasId(String id);
    boolean register(String id, String pwd, String email);
}
