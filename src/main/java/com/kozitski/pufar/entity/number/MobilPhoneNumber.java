package com.kozitski.pufar.entity.number;

/**
 * The Class MobilPhoneNumber.
 */
public class MobilPhoneNumber {
    
    /** The Constant BELARUS_CODE. */
    public static final String BELARUS_CODE = "+375";

    /** The mobil phone number id. */
    private long mobilPhoneNumberId;
    
    /** The country. */
    private String country;
    
    /** The operator. */
    private String operator;
    
    /** The number. */
    private String number;

    /**
     * Instantiates a new mobil phone number.
     */
    public MobilPhoneNumber() {
    }
    
    /**
     * Instantiates a new mobil phone number.
     *
     * @param mobilPhoneNumberId the mobil phone number id
     * @param country the country
     * @param operator the operator
     * @param number the number
     */
    public MobilPhoneNumber(long mobilPhoneNumberId, String country, String operator, String number) {
        this.mobilPhoneNumberId = mobilPhoneNumberId;
        this.country = country;
        this.operator = operator;
        this.number = number;
    }

    /**
     * Gets the mobil phone number id.
     *
     * @return the mobil phone number id
     */
    public long getMobilPhoneNumberId() {
        return mobilPhoneNumberId;
    }
    
    /**
     * Sets the mobil phone number id.
     *
     * @param mobilPhoneNumberId the new mobil phone number id
     */
    public void setMobilPhoneNumberId(long mobilPhoneNumberId) {
        this.mobilPhoneNumberId = mobilPhoneNumberId;
    }
    
    /**
     * Gets the country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Gets the operator.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * Sets the operator.
     *
     * @param operator the new operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    /**
     * Gets the number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }
    
    /**
     * Sets the number.
     *
     * @param number the new number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
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
    
    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = (int) (getMobilPhoneNumberId() ^ (getMobilPhoneNumberId() >>> 32));
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getOperator() != null ? getOperator().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        return result;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return country + " (" + operator + ") " + number;
    }

}


