package com.yq.train.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    COURSE_NOT_FOUND(2001,"课程不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中问题或问题不存在"),
    NO_LOGIN(2003,"请登录后再操作"),
    SYS_ERROR(2004,"服务器错误！"),
    NO_iNAME(2005,"用户名不能为空"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在了要不换个试试"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"无法阅读别人的信息"),
    NOTIFICATION_NOT_FOUND(2009,"消息不见了" +
            "");

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
