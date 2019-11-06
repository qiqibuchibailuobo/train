package com.yq.train.mapper;

import com.yq.train.model.Course;
import com.yq.train.model.CourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CourseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    long countByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int deleteByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int insert(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int insertSelective(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    List<Course> selectByExampleWithRowbounds(CourseExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    List<Course> selectByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    Course selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int updateByPrimaryKeySelective(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 06 19:03:09 CST 2019
     */
    int updateByPrimaryKey(Course record);
}