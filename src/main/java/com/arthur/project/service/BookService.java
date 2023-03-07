package com.arthur.project.service;

import com.arthur.project.dataObject.BookBaseDO;
import com.arthur.project.dataObject.BookDetailDO;
import com.arthur.project.error.ProjectException;
import com.arthur.project.service.model.BookModel;
import com.arthur.project.service.model.LendModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// @Service(value = "bookImpl")
public interface BookService {
    List<BookModel> initBookData();
    List<LendModel> initLendData();
    List<BookDetailDO> getBookDetail(Integer book_base_no);

    List<LendModel> getMyLend(String userId);

    boolean lendBook(Map<String,String> info) throws ProjectException;
    boolean returnBook(Map<String,String> info) throws ProjectException;
    boolean addBook(BookBaseDO bookBaseDO) throws ProjectException;
}
