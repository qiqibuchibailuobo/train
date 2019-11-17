package com.yq.train.model;

import java.util.Date;

public class Course {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.teaching_id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private Integer teachingId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.course_describe
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private String courseDescribe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.price
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private String price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.student_count
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private Integer studentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.gmt_create
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.gmt_modified
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.headportrait_url
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private String headportraitUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course.class_time
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    private String classTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.id
     *
     * @return the value of course.id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.id
     *
     * @param id the value for course.id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.teaching_id
     *
     * @return the value of course.teaching_id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public Integer getTeachingId() {
        return teachingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.teaching_id
     *
     * @param teachingId the value for course.teaching_id
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setTeachingId(Integer teachingId) {
        this.teachingId = teachingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.course_describe
     *
     * @return the value of course.course_describe
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public String getCourseDescribe() {
        return courseDescribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.course_describe
     *
     * @param courseDescribe the value for course.course_describe
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setCourseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe == null ? null : courseDescribe.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.price
     *
     * @return the value of course.price
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public String getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.price
     *
     * @param price the value for course.price
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.student_count
     *
     * @return the value of course.student_count
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public Integer getStudentCount() {
        return studentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.student_count
     *
     * @param studentCount the value for course.student_count
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.gmt_create
     *
     * @return the value of course.gmt_create
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.gmt_create
     *
     * @param gmtCreate the value for course.gmt_create
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.gmt_modified
     *
     * @return the value of course.gmt_modified
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.gmt_modified
     *
     * @param gmtModified the value for course.gmt_modified
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.headportrait_url
     *
     * @return the value of course.headportrait_url
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public String getHeadportraitUrl() {
        return headportraitUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.headportrait_url
     *
     * @param headportraitUrl the value for course.headportrait_url
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setHeadportraitUrl(String headportraitUrl) {
        this.headportraitUrl = headportraitUrl == null ? null : headportraitUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course.class_time
     *
     * @return the value of course.class_time
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public String getClassTime() {
        return classTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course.class_time
     *
     * @param classTime the value for course.class_time
     *
     * @mbg.generated Sun Nov 17 21:55:01 CST 2019
     */
    public void setClassTime(String classTime) {
        this.classTime = classTime == null ? null : classTime.trim();
    }
}