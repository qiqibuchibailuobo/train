package com.yq.train.model;

public class Student {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.NAME
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.CLS
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    private String cls;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.ADDRESS_ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    private Integer addressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.age
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    private Integer age;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.ID
     *
     * @return the value of student.ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.ID
     *
     * @param id the value for student.ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.NAME
     *
     * @return the value of student.NAME
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.NAME
     *
     * @param name the value for student.NAME
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.CLS
     *
     * @return the value of student.CLS
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public String getCls() {
        return cls;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.CLS
     *
     * @param cls the value for student.CLS
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public void setCls(String cls) {
        this.cls = cls == null ? null : cls.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.ADDRESS_ID
     *
     * @return the value of student.ADDRESS_ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.ADDRESS_ID
     *
     * @param addressId the value for student.ADDRESS_ID
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.age
     *
     * @return the value of student.age
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.age
     *
     * @param age the value for student.age
     *
     * @mbg.generated Fri Nov 01 16:53:38 CST 2019
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}