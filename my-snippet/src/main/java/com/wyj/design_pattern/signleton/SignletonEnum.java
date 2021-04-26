package com.wyj.design_pattern.signleton;

public enum SignletonEnum {
    INSTANCE;

    public SignletonEnum getInstance(){
        return INSTANCE;
    }
}
