package com.arthur.project.service.impl;

import com.arthur.project.dao.BookBaseDOMapper;
import com.arthur.project.dao.BookDetailDOMapper;
import com.arthur.project.dao.LendDOMapper;
import com.arthur.project.dao.UserDOMapper;
import com.arthur.project.dataObject.BookBaseDO;
import com.arthur.project.dataObject.BookDetailDO;
import com.arthur.project.dataObject.LendDO;
import com.arthur.project.dataObject.UserDO;
import com.arthur.project.error.EnumBaseError;
import com.arthur.project.error.ProjectException;
import com.arthur.project.service.BookService;
import com.arthur.project.service.model.BookModel;
import com.arthur.project.service.model.LendModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("BookImpl")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookBaseDOMapper bookBaseDOMapper;

    @Autowired
    private LendDOMapper lendDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private BookDetailDOMapper bookDetailDOMapper;

    @Override
    public List<BookModel> initBookData() {
        List<BookBaseDO> bookBaseDOList = bookBaseDOMapper.selectAll();
        List<BookModel> bookModelList = bookBaseDOList.stream().map(bookBaseDO -> {
            if (bookBaseDO==null){
                return null;
            }
            BookModel bookModel = new BookModel();
            BeanUtils.copyProperties(bookBaseDO, bookModel);
            return bookModel;
        }).collect(Collectors.toList());
        return bookModelList;
    }

    @Override
    public List<LendModel> initLendData() {
        List<LendDO> lendDOList = lendDOMapper.selectAll();
        List<LendModel> lendModelList = lendDOList.stream().map(lendDO -> {
            BookDetailDO bookDetailDO = bookDetailDOMapper.selectByPrimaryKey(lendDO.getLendBookNo());

            BookBaseDO bookBaseDO = bookBaseDOMapper.selectByPrimaryKey(bookDetailDO.getDetailBase());

            UserDO userDO = userDOMapper.selectByPrimaryKey(lendDO.getLendUserNo());

            LendModel lendModel = new LendModel();
            try {
                lendModel = convertToLendModel(bookDetailDO, bookBaseDO, userDO, lendDO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lendModel.setTime(this.formatDate(lendModel.getLendTime()));

            if (lendModel.getLendReturnTime().equals("")){
                lendModel.setOverTime(this.isOverTime(lendModel.getLendTime()));
            }else {
                lendModel.setOverTime(false);
            }

            lendModel.setLendTime(null);
            return lendModel;
        }).collect(Collectors.toList());
        return lendModelList;
    }


    @Override
    public List<BookDetailDO> getBookDetail(Integer book_base_no) {
        List<BookDetailDO> bookDetailDOList = bookDetailDOMapper.selectByBase(book_base_no);
        return bookDetailDOList;
    }

    @Override
    public List<LendModel> getMyLend(String userId) {
        List<LendDO> lendDOList = lendDOMapper.selectByUserNo(Integer.parseInt(userId));
        List<LendModel> lendModelList = lendDOList.stream().map(lendDO -> {
            BookDetailDO bookDetailDO = bookDetailDOMapper.selectByPrimaryKey(lendDO.getLendBookNo());

            BookBaseDO bookBaseDO = bookBaseDOMapper.selectByPrimaryKey(bookDetailDO.getDetailBase());

            UserDO userDO = userDOMapper.selectByPrimaryKey(lendDO.getLendUserNo());

            LendModel lendModel = new LendModel();
            try {
                lendModel = convertToLendModel(bookDetailDO, bookBaseDO, userDO, lendDO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lendModel.setTime(this.formatDate(lendModel.getLendTime()));

            if (lendModel.getLendReturnTime().equals("")){
                lendModel.setOverTime(this.isOverTime(lendModel.getLendTime()));
            }else {
                lendModel.setOverTime(false);
            }

            lendModel.setLendTime(null);
            return lendModel;
        }).collect(Collectors.toList());
        return lendModelList;
    }

    @Override
    public boolean lendBook(Map<String,String> info) throws ProjectException {
        String userName =  info.get("userName");
        String bookId = info.get("bookId");
        UserDO userDO = userDOMapper.selectByName(userName);

        if (judgeLendNum(lendDOMapper.selectByUserNo(userDO.getUserId()))){

            BookDetailDO bookDetailDO = bookDetailDOMapper.selectByBookId(bookId);
            if (bookDetailDO.getDetailState().equals("1000")){

                LendDO lendDO = new LendDO();
                lendDO.setLendBookNo(bookDetailDO.getDetailNo());
                lendDO.setLendUserNo(userDO.getUserId());

                lendDOMapper.insertSelective(lendDO);

                // 把借书记录和书关联起来,并且修改书籍状态
                bookDetailDO.setDetailLend(lendDO.getLendNo());
                bookDetailDO.setDetailState("0111");

                bookDetailDOMapper.updateByPrimaryKey(bookDetailDO);

                return true;
            }else{
                throw new ProjectException(EnumBaseError.BOOK_HAVE_LEND);
            }
        }else {
            throw new ProjectException(EnumBaseError.BOOK_NUM_MAX);
        }
    }

    @Override
    public boolean returnBook(Map<String,String> info) throws ProjectException {
        String bookId = info.get("bookId");
        String userName = info.get("userName");
        BookDetailDO bookDetailDO = bookDetailDOMapper.selectByBookId(bookId);


        if (bookDetailDO.getDetailState().equals("0111")){
            LendDO lendDO = lendDOMapper.selectByPrimaryKey(bookDetailDO.getDetailLend());
            UserDO userDO = userDOMapper.selectByPrimaryKey(lendDO.getLendUserNo());

            if (userDO.getUserName().equals(userName)){
                // 添加还书时间
                lendDO.setLendReturnTime(this.getDate());

                // 修改书籍状态
                bookDetailDO.setDetailState("1000");
                bookDetailDO.setDetailLend(null);

                bookDetailDOMapper.updateByPrimaryKey(bookDetailDO);
                lendDOMapper.updateByPrimaryKey(lendDO);

                return true;

            }else {
                throw new ProjectException(EnumBaseError.BOOK_NOT_MATCH_USER);
            }
        }else {
            throw new ProjectException(EnumBaseError.BOOK_HAVENT_LEND);
        }
    }

    @Override
    public boolean addBook(BookBaseDO bookBaseDO) throws ProjectException {
        try {
            bookBaseDOMapper.insertSelective(bookBaseDO);
        }catch (DuplicateKeyException e){
            throw new ProjectException(EnumBaseError.BOOK_ID_HAVE_USED);
        }
        String baseId = bookBaseDO.getBookId();
        Integer detailBase = bookBaseDO.getBookNo();
        int number = Integer.parseInt(bookBaseDO.getBookNumber());

        for(int i =0 ; i<number; i++){
            String temp = null;
            if (i<9){
                temp = baseId + "00" + (i+1);
            }
            else if (i<99){
                temp = baseId + "0" + (i+1);
            }else {
                temp = baseId + (i+1);
            }

            BookDetailDO bookDetailDO = new BookDetailDO();
            bookDetailDO.setDetailBase(detailBase);
            bookDetailDO.setDetailId(temp);

            bookDetailDOMapper.insertSelective(bookDetailDO);
        }

        return true;
    }

    private LendModel convertToLendModel(BookDetailDO bookDetailDO,BookBaseDO bookBaseDO, UserDO userDO, LendDO lendDO) throws ParseException {
        if (lendDO  == null | userDO == null | bookBaseDO == null){
            return  null;
        }
        LendModel lendModel = new LendModel();
        BeanUtils.copyProperties(lendDO, lendModel);
        lendModel.setBookName(bookBaseDO.getBookName());
        lendModel.setUserName(userDO.getUserName());
        lendModel.setDetailId(bookDetailDO.getDetailId());

        return lendModel;
    }



    /*
    * 功能函数区域
    * */

    private String formatDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    private String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(System.currentTimeMillis());
        return df.format(now);
    }

    private Boolean isOverTime(Date targetDate){
        long time = targetDate.getTime();
        long now = Calendar.getInstance().getTime().getTime();
        int gap = (int)(now - time)/(1000*60*60*24);
        if (gap>7){
            return true;
        }else {
            return false;
        }
    }

    private boolean judgeLendNum(List<LendDO> lendDOList){
        int result = 0;
        for (LendDO lendDO:
             lendDOList) {
            if (lendDO.getLendReturnTime() == null | lendDO.getLendReturnTime() == ""){
                result ++;
            }
        }
        if (result<=2){
            return true;
        }else {
            return false;
        }
    }
}