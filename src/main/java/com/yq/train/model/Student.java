package com.yq.train.model;

import java.util.Date;

public class Student {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.id
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.user_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.user_pwd
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private String userPwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.student_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private String studentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.tel
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private Integer tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.add_teacher
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private Integer addTeacher;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.gmt_create
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.gmt_modified
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.headportrait_url
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    private String headportraitUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.id
     *
     * @return the value of student.id
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.id
     *
     * @param id the value for student.id
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.user_name
     *
     * @return the value of student.user_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.user_name
     *
     * @param userName the value for student.user_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.user_pwd
     *
     * @return the value of student.user_pwd
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.user_pwd
     *
     * @param userPwd the value for student.user_pwd
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.student_name
     *
     * @return the value of student.student_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.student_name
     *
     * @param studentName the value for student.student_name
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.tel
     *
     * @return the value of student.tel
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public Integer getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.tel
     *
     * @param tel the value for student.tel
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setTel(Integer tel) {
        this.tel = tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.add_teacher
     *
     * @return the value of student.add_teacher
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public Integer getAddTeacher() {
        return addTeacher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.add_teacher
     *
     * @param addTeacher the value for student.add_teacher
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setAddTeacher(Integer addTeacher) {
        this.addTeacher = addTeacher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.gmt_create
     *
     * @return the value of student.gmt_create
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.gmt_create
     *
     * @param gmtCreate the value for student.gmt_create
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.gmt_modified
     *
     * @return the value of student.gmt_modified
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.gmt_modified
     *
     * @param gmtModified the value for student.gmt_modified
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.headportrait_url
     *
     * @return the value of student.headportrait_url
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public String getHeadportraitUrl() {
        return headportraitUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.headportrait_url
     *
     * @param headportraitUrl the value for student.headportrait_url
     *
     * @mbg.generated Fri Nov 01 21:41:50 CST 2019
     */
    public void setHeadportraitUrl(String headportraitUrl) {
        this.headportraitUrl = headportraitUrl == null ? null : headportraitUrl.trim();
    }
}