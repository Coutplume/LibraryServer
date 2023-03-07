package com.arthur.project.controller;


import com.arthur.project.dataObject.BookBaseDO;
import com.arthur.project.error.EnumBaseError;
import com.arthur.project.error.ProjectException;
import com.arthur.project.response.CommonReturnType;
import com.arthur.project.service.BookService;
import com.arthur.project.service.model.BookModel;
import com.arthur.project.service.model.LendModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

@RequestMapping("/info")
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@Controller
public class InfoController extends BaseController{
    @Autowired
    private BookService bookService;


    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/lendInfo", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType initLendInfo() throws ProjectException {
        try{
            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();
            if (!userType.equals("0111")){
                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
            }
            List<LendModel> lendModelList = bookService.initLendData();
            return CommonReturnType.create(lendModelList);

        }catch (Exception e){
            throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/bookInfo", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType initBookInfo() throws ProjectException {
        try{
//            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();
//            if (!userType.equals("0111")){
//                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
//            }
            List<BookModel> bookBaseDOList = bookService.initBookData();
            return CommonReturnType.create(bookBaseDOList);

        }catch (Exception e){
            throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
        }
    }

//    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
//    @RequestMapping(value = "/bookDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonReturnType getBookDetail(@RequestParam("BookBaseNo") Integer no) throws ProjectException {
//        try{
//            String userType = httpServletRequest.getSession().getAttribute("PRIVILEGE").toString();
//            if (!userType.equals("0111")){
//                throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
//            }
//            return CommonReturnType.create(bookService.getBookDetail(no));
//
//        }catch (Exception e){
//            throw new ProjectException(EnumBaseError.PRIVILEGE_ERROR);
//        }
//    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/return", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public  CommonReturnType returnBook(@RequestBody Map<String, String> info) throws ProjectException{
        try {
            if (info == null){
                throw new ProjectException(EnumBaseError.PARAMETER_VALIDATION_ERROR);
            }
            if (bookService.returnBook(info)){
                return CommonReturnType.create("还书成功！");
            }else {
                return CommonReturnType.create("还书失败！","fail");
            }
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof NullPointerException){
                throw new ProjectException(EnumBaseError.BOOK_OR_USER_NOT_IN_STORE);
            }else {
                throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
            }
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/lend", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public  CommonReturnType lendBook(@RequestBody Map<String, String> info) throws ProjectException{
        try {
            if (info == null){
                throw new ProjectException(EnumBaseError.PARAMETER_VALIDATION_ERROR);
            }
            if (bookService.lendBook(info)){
                return CommonReturnType.create("还书成功！");
            }else {
                return CommonReturnType.create("还书失败！","fail");
            }
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof NullPointerException){
                throw new ProjectException(EnumBaseError.BOOK_OR_USER_NOT_IN_STORE);
            }else if (e instanceof ProjectException){
                throw e;
            }else {
                e.printStackTrace();
                throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
            }
        }
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/add", method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType addBook(@RequestBody BookBaseDO bookBaseDO) throws ProjectException {
        bookService.addBook(bookBaseDO);
        return CommonReturnType.create("success");
    }

    @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
    @RequestMapping(value = "/myLend",method = {RequestMethod.POST},consumes = {CONTENT_TYPE})
    @ResponseBody
    public CommonReturnType getMyLend(@RequestBody Map<String, String> info) throws ProjectException {
        try {
            String userId = info.get("userId");
            return CommonReturnType.create(bookService.getMyLend(userId));

        }catch (NullPointerException e){
            e.printStackTrace();
            throw new ProjectException(EnumBaseError.UNKNOWN_ERROR);
        }
    }


}
