package com.arthur.project.dao;

import com.arthur.project.dataObject.BookBaseDO;

import java.util.List;

public interface BookBaseDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    int deleteByPrimaryKey(Integer bookNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    int insert(BookBaseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    int insertSelective(BookBaseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    BookBaseDO selectByPrimaryKey(Integer bookNo);

    List<BookBaseDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(BookBaseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_base
     *
     * @mbg.generated Wed Mar 16 18:42:14 GMT+08:00 2022
     */
    int updateByPrimaryKey(BookBaseDO record);
}