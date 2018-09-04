package com.weizhi.redflower.enums;

public enum UserInfoStateEnum {
    /**
     * 用户信息没有完善：0
     */
    INCOMPLETED(0),

    /**
     * 用户信息已经完善
     */
    COMPLETED(1);

    private Integer state ;

    UserInfoStateEnum(int state){
        this.state=state;
    }

    public Integer getState() {
        return state;
    }
}
