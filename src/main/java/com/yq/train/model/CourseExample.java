package com.yq.train.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CourseExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public CourseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTeachingIdIsNull() {
            addCriterion("teaching_id is null");
            return (Criteria) this;
        }

        public Criteria andTeachingIdIsNotNull() {
            addCriterion("teaching_id is not null");
            return (Criteria) this;
        }

        public Criteria andTeachingIdEqualTo(Integer value) {
            addCriterion("teaching_id =", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdNotEqualTo(Integer value) {
            addCriterion("teaching_id <>", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdGreaterThan(Integer value) {
            addCriterion("teaching_id >", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("teaching_id >=", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdLessThan(Integer value) {
            addCriterion("teaching_id <", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdLessThanOrEqualTo(Integer value) {
            addCriterion("teaching_id <=", value, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdIn(List<Integer> values) {
            addCriterion("teaching_id in", values, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdNotIn(List<Integer> values) {
            addCriterion("teaching_id not in", values, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdBetween(Integer value1, Integer value2) {
            addCriterion("teaching_id between", value1, value2, "teachingId");
            return (Criteria) this;
        }

        public Criteria andTeachingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("teaching_id not between", value1, value2, "teachingId");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeIsNull() {
            addCriterion("course_describe is null");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeIsNotNull() {
            addCriterion("course_describe is not null");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeEqualTo(String value) {
            addCriterion("course_describe =", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeNotEqualTo(String value) {
            addCriterion("course_describe <>", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeGreaterThan(String value) {
            addCriterion("course_describe >", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("course_describe >=", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeLessThan(String value) {
            addCriterion("course_describe <", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeLessThanOrEqualTo(String value) {
            addCriterion("course_describe <=", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeLike(String value) {
            addCriterion("course_describe like", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeNotLike(String value) {
            addCriterion("course_describe not like", value, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeIn(List<String> values) {
            addCriterion("course_describe in", values, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeNotIn(List<String> values) {
            addCriterion("course_describe not in", values, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeBetween(String value1, String value2) {
            addCriterion("course_describe between", value1, value2, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andCourseDescribeNotBetween(String value1, String value2) {
            addCriterion("course_describe not between", value1, value2, "courseDescribe");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(String value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(String value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(String value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(String value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(String value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(String value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLike(String value) {
            addCriterion("price like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotLike(String value) {
            addCriterion("price not like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<String> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<String> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(String value1, String value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(String value1, String value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andStudentCountIsNull() {
            addCriterion("student_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentCountIsNotNull() {
            addCriterion("student_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentCountEqualTo(Integer value) {
            addCriterion("student_count =", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotEqualTo(Integer value) {
            addCriterion("student_count <>", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountGreaterThan(Integer value) {
            addCriterion("student_count >", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("student_count >=", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountLessThan(Integer value) {
            addCriterion("student_count <", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountLessThanOrEqualTo(Integer value) {
            addCriterion("student_count <=", value, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountIn(List<Integer> values) {
            addCriterion("student_count in", values, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotIn(List<Integer> values) {
            addCriterion("student_count not in", values, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountBetween(Integer value1, Integer value2) {
            addCriterion("student_count between", value1, value2, "studentCount");
            return (Criteria) this;
        }

        public Criteria andStudentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("student_count not between", value1, value2, "studentCount");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterionForJDBCDate("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterionForJDBCDate("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterionForJDBCDate("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterionForJDBCDate("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterionForJDBCDate("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterionForJDBCDate("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterionForJDBCDate("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterionForJDBCDate("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlIsNull() {
            addCriterion("headportrait_url is null");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlIsNotNull() {
            addCriterion("headportrait_url is not null");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlEqualTo(String value) {
            addCriterion("headportrait_url =", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlNotEqualTo(String value) {
            addCriterion("headportrait_url <>", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlGreaterThan(String value) {
            addCriterion("headportrait_url >", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlGreaterThanOrEqualTo(String value) {
            addCriterion("headportrait_url >=", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlLessThan(String value) {
            addCriterion("headportrait_url <", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlLessThanOrEqualTo(String value) {
            addCriterion("headportrait_url <=", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlLike(String value) {
            addCriterion("headportrait_url like", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlNotLike(String value) {
            addCriterion("headportrait_url not like", value, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlIn(List<String> values) {
            addCriterion("headportrait_url in", values, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlNotIn(List<String> values) {
            addCriterion("headportrait_url not in", values, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlBetween(String value1, String value2) {
            addCriterion("headportrait_url between", value1, value2, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andHeadportraitUrlNotBetween(String value1, String value2) {
            addCriterion("headportrait_url not between", value1, value2, "headportraitUrl");
            return (Criteria) this;
        }

        public Criteria andClassTimeIsNull() {
            addCriterion("class_time is null");
            return (Criteria) this;
        }

        public Criteria andClassTimeIsNotNull() {
            addCriterion("class_time is not null");
            return (Criteria) this;
        }

        public Criteria andClassTimeEqualTo(String value) {
            addCriterion("class_time =", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotEqualTo(String value) {
            addCriterion("class_time <>", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeGreaterThan(String value) {
            addCriterion("class_time >", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeGreaterThanOrEqualTo(String value) {
            addCriterion("class_time >=", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLessThan(String value) {
            addCriterion("class_time <", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLessThanOrEqualTo(String value) {
            addCriterion("class_time <=", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLike(String value) {
            addCriterion("class_time like", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotLike(String value) {
            addCriterion("class_time not like", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeIn(List<String> values) {
            addCriterion("class_time in", values, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotIn(List<String> values) {
            addCriterion("class_time not in", values, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeBetween(String value1, String value2) {
            addCriterion("class_time between", value1, value2, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotBetween(String value1, String value2) {
            addCriterion("class_time not between", value1, value2, "classTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table course
     *
     * @mbg.generated do_not_delete_during_merge Wed Nov 20 21:25:19 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table course
     *
     * @mbg.generated Wed Nov 20 21:25:19 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}