package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;

public interface RegisterServiceImpl extends ServiceImpl {
    boolean register(String id, String pwd, String email);
    boolean register(UserBean bean);
}
