package xd.service;

import xd.model.UserBean;
import xd.repository.UserRepository;

public interface LoginService extends Service {
    UserBean login(String id, String pwd);
    boolean logout(UserRepository repository);
}
