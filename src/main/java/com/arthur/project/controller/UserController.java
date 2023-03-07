package com.arthur.project.controller;


import com.arthur.project.dao.UserDOMapper;
import com.arthur.project.dataObject.UserDO;
import com.arthur.project.error.EnumBaseError;
import com.arthur.project.error.ProjectException;
import com.arthur.project.response.CommonReturnType;
import com.arthur.project.service.UserService;
import com.arthur.project.service.model.UserModel;
import com.arthur.project.session.MySessionContext;
import com.arthur.project.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.PastOrPresent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

@RequestMapping("/user")
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDOMapper userDOMapper;

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/login", consumes = CONTENT_TYPE, method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType userLogin(@RequestBody UserModel userModel) throws ProjectException, NoSuchAlgorithmException {
        setSession();
        // 首先对密码进行加密处理
        userModel.setUserPwd(encreptPassWord(userModel.getUserPwd()));
        if (userService.userLogin(userModel)){
            httpServletRequest.getSession().setAttribute("PRIVILEGE", userModel.getUserType());
            return CommonReturnType.create(userModel);
        }else {
            return CommonReturnType.create("FAIL", "fail");
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/verify")
    @ResponseBody
    public CommonReturnType verifyPrivilege() throws ProjectException, NoSuchAlgorithmException {
        //setSession();
        try{
            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();

            if (userType == null){
                return CommonReturnType.create("Error", "fail");
            }else {
                return CommonReturnType.create(userType);
            }

        }catch (Exception e){
            throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/register", consumes = CONTENT_TYPE, method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType userRegister(@RequestBody UserModel userModel) throws ProjectException, NoSuchAlgorithmException {
        //setSession();
        userModel.setUserPwd(encreptPassWord(userModel.getUserPwd()));
        try{
            boolean result = userService.register(userModel);
            if (result){
                return CommonReturnType.create("注册成功！");
            }
        }catch (DuplicateKeyException e){
            throw new ProjectException(EnumBaseError.DUPLICATE_ERROR);
        }
        return CommonReturnType.create("ERROR","fail");
    }


    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType getOtp(@RequestBody Map<String,String> telephoneInfo){
        //setSession();
        //获取Otp
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将Otp同对应用户手机号关联,对于分布式应用一般使用redis，这里只简单使用httpSession进行绑定
        httpServletRequest.getSession().setAttribute(telephoneInfo.get("telephone"),otpCode);
        //将Otp发送给用户，忽略
        System.out.println("Telephone:" + telephoneInfo.get("telephone") + "& Otp:" + httpServletRequest.getSession().getAttribute(telephoneInfo.get("telephone")));


        //httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=xxx;SameSite=None;Secure");

        return CommonReturnType.create(otpCode);
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/changePhone", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType changePhone(@RequestBody Map<String, String> phoneInfo) throws ProjectException, NoSuchAlgorithmException {
        //setSession();
        try {
            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();
            String otp =  httpServletRequest.getSession().getAttribute(phoneInfo.get("newPhone")).toString();
            if (otp.equals(phoneInfo.get("otp"))){
                if (userService.changePhone(phoneInfo.get("userName"),phoneInfo.get("newPhone"))){
                    return CommonReturnType.create("修改绑定手机号成功！");
                }else {
                    return CommonReturnType.create("修改绑定手机号失败！","fail");
                }
            }else {
                throw  new ProjectException(EnumBaseError.OTP_ERROR);
            }
        }catch (Exception e){
            if (e instanceof NullPointerException ) {
                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
            }else if (e instanceof DuplicateKeyException){
                throw new ProjectException(EnumBaseError.DUPLICATE_ERROR);
            }else {
                throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
            }
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/changePwd", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType changePwd(@RequestBody Map<String, String> pwdInfo) throws ProjectException, NoSuchAlgorithmException {
        try {
            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();
            String oldPwd = encreptPassWord(pwdInfo.get("oldPwd"));
            String newPwd = encreptPassWord(pwdInfo.get("newPwd"));
            String userName = pwdInfo.get("userName");
            if (userService.forgetPwd(userName, newPwd, oldPwd)){
                return CommonReturnType.create("修改密码成功！");
            }else {
                return CommonReturnType.create("修改密码失败！","fail");
            }

        }catch (Exception e){
            if (e instanceof NullPointerException){
                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
            }else {
                throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
            }
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/forgetPwd", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType forgetPwd(@RequestBody Map<String, String> pwdInfo) throws ProjectException, NoSuchAlgorithmException {
        //setSession();
        try {
            if (pwdInfo.get("otp") == null){
                UserDO userDO = userDOMapper.selectByName(pwdInfo.get("userName"));

                Random random = new Random();
                int randomInt = random.nextInt(99999);
                randomInt += 10000;
                String otpCode = String.valueOf(randomInt);

                httpServletRequest.getSession().setAttribute(userDO.getUserPhone(), otpCode);
                return CommonReturnType.create(otpCode);
            }else {
                UserDO userDO = userDOMapper.selectByName(pwdInfo.get("userName"));
                if (pwdInfo.get("otp").equals(httpServletRequest.getSession().getAttribute(userDO.getUserPhone()))){
                    userDO.setUserPwd(encreptPassWord(pwdInfo.get("newPwd")));
                   if ( userDOMapper.updateByPrimaryKey(userDO)>0){
                       return CommonReturnType.create("找回成功！");
                   }else {
                       return CommonReturnType.create("找回失败！","fail");
                   }
                }else {
                    throw new ProjectException(EnumBaseError.OTP_ERROR);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof NullPointerException){
                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
            }else {
                throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
            }
        }
    }


    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/test")
    @ResponseBody
    public CommonReturnType test() throws ProjectException, NoSuchAlgorithmException {
        return CommonReturnType.create(userService.test());
    }


    public  String encreptPassWord(String pwd) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Base64.Encoder encoder = Base64.getEncoder();
        String result = encoder.encodeToString(messageDigest.digest(pwd.getBytes(StandardCharsets.UTF_8)));
        return result;
    }
}
