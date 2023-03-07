package com.arthur.project.service;

import com.arthur.project.dataObject.UserDO;
import com.arthur.project.service.model.UserModel;

public interface UserService {
    boolean userLogin(UserModel userModel);
    boolean forgetPwd(String userName, String newPwd, String oldPwd);
    boolean register(UserModel userModel);
    boolean changePhone(String userName, String newPhone);
    UserDO test();
}
