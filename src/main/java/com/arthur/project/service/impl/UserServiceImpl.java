package com.arthur.project.service.impl;

import com.arthur.project.dao.UserDOMapper;
import com.arthur.project.dataObject.UserDO;
import com.arthur.project.service.UserService;
import com.arthur.project.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public boolean userLogin(UserModel userModel) {
        UserDO userDO = userDOMapper.selectByName(userModel.getUserName());
        if (userDO.getUserPwd().equals(userModel.getUserPwd())){
            userModel.setUserType(userDO.getUserType());
            userModel.setUserPhone(userDO.getUserPhone());
            userModel.setUserId(userDO.getUserId());
            userModel.setUserPwd("");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean forgetPwd(String userName, String newPwd, String oldPwd) {
        UserDO userDO = userDOMapper.selectByName(userName);
        if (userDO.getUserPwd().equals(oldPwd)){
            userDO.setUserPwd(newPwd);
            if (userDOMapper.updateByPrimaryKey(userDO)>0){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean register(UserModel userModel) throws DuplicateKeyException {
        UserDO userDO = this.convertToUserDO(userModel);
        int result = userDOMapper.insertSelective(userDO);
        if (result > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean changePhone(String userName, String newPhone) {
        UserDO userDO = userDOMapper.selectByName(userName);
        if (userDO == null){
            return false;
        }
        userDO.setUserPhone(newPhone);
        int result = userDOMapper.updateByPrimaryKey(userDO);
        if (result >0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public UserDO test() {
        return userDOMapper.selectByName("1");
    }


    private UserDO convertToUserDO(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }
}
