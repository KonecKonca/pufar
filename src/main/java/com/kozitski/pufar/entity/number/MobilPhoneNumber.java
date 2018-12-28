package com.kozitski.pufar.entity.number;

public class MobilPhoneNumber {
    public static final String BELARUS_CODE = "+375";

    private long mobilPhoneNumberId;
    private String country;
    private String operator;
    private String number;

    public MobilPhoneNumber() {
    }
    public MobilPhoneNumber(long mobilPhoneNumberId, String country, String operator, String number) {
        this.mobilPhoneNumberId = mobilPhoneNumberId;
        this.country = country;
        this.operator = operator;
        this.number = number;
    }

    public long getMobilPhoneNumberId() {
        return mobilPhoneNumberId;
    }
    public void setMobilPhoneNumberId(long mobilPhoneNumberId) {
        this.mobilPhoneNumberId = mobilPhoneNumberId;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobilPhoneNumber)) return false;

        MobilPhoneNumber that = (MobilPhoneNumber) o;

        if (getMobilPhoneNumberId() != that.getMobilPhoneNumberId()) return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getOperator() != null ? !getOperator().equals(that.getOperator()) : that.getOperator() != null)
            return false;
        return getNumber() != null ? getNumber().equals(that.getNumber()) : that.getNumber() == null;
    }
    @Override
    public int hashCode() {
        int result = (int) (getMobilPhoneNumberId() ^ (getMobilPhoneNumberId() >>> 32));
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getOperator() != null ? getOperator().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return country + " (" + operator + ") " + number;
    }

}


