package com.apress.prospring5.ch10.sec5.obj;

public enum Gender {
    MALE("M"), FEMALE("F");
    private String code;
    Gender(String code){
        this.code = code;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
