package com.kozitski.pufar.annotation.engine.validation.annotation;


// make with builder
public class ValidateParameterTransfer {
    private int minLength;
    private int maxLength;
    private String regExp;
    private String forbiddenValue;


    public int getMinLength() {
        return minLength;
    }
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
    public int getMaxLength() {
        return maxLength;
    }
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    public String getRegExp() {
        return regExp;
    }
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }
    public String getForbiddenValue() {
        return forbiddenValue;
    }
    public void setForbiddenValue(String forbiddenValue) {
        this.forbiddenValue = forbiddenValue;
    }


    @Override
    public String toString() {
        return "ValidateParameterTransfer{" +
                "minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", regExp='" + regExp + '\'' +
                ", forbiddenValue='" + forbiddenValue + '\'' +
                '}';
    }

}
