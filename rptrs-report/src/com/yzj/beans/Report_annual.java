package com.yzj.beans;

import java.util.Date;

public class Report_annual {
    String GENERAL_REL_TRAN_NUMBER;
    String MAJOR_REL_TRAN_NUMBER;
    String MAJOR_REL_TRAN_AMOUNT;
    String GENERAL_REL_TRAN_AMOUNT;
    String TRAN_AMOUNT;

    public String getGENERAL_REL_TRAN_NUMBER() {
        if (GENERAL_REL_TRAN_NUMBER == null || GENERAL_REL_TRAN_NUMBER.trim() == "") {
            return "0";
        }
        return GENERAL_REL_TRAN_NUMBER;
    }

    public void setGENERAL_REL_TRAN_NUMBER(String GENERAL_REL_TRAN_NUMBER) {
        this.GENERAL_REL_TRAN_NUMBER = GENERAL_REL_TRAN_NUMBER;
    }

    public String getMAJOR_REL_TRAN_NUMBER() {
        if (MAJOR_REL_TRAN_NUMBER == null || MAJOR_REL_TRAN_NUMBER.trim() == "") {
            return "0";
        }
        return MAJOR_REL_TRAN_NUMBER;
    }

    public void setMAJOR_REL_TRAN_NUMBER(String MAJOR_REL_TRAN_NUMBER) {
        this.MAJOR_REL_TRAN_NUMBER = MAJOR_REL_TRAN_NUMBER;
    }

    public String getMAJOR_REL_TRAN_AMOUNT() {
        if (MAJOR_REL_TRAN_AMOUNT == null || MAJOR_REL_TRAN_AMOUNT.trim() == "") {
            return "0";
        }
        return MAJOR_REL_TRAN_AMOUNT;
    }

    public void setMAJOR_REL_TRAN_AMOUNT(String MAJOR_REL_TRAN_AMOUNT) {
        this.MAJOR_REL_TRAN_AMOUNT = MAJOR_REL_TRAN_AMOUNT;
    }

    public String getGENERAL_REL_TRAN_AMOUNT() {
        if (GENERAL_REL_TRAN_AMOUNT == null || GENERAL_REL_TRAN_AMOUNT.trim() == "") {
            return "0";
        }
        return GENERAL_REL_TRAN_AMOUNT;
    }

    public void setGENERAL_REL_TRAN_AMOUNT(String GENERAL_REL_TRAN_AMOUNT) {
        this.GENERAL_REL_TRAN_AMOUNT = GENERAL_REL_TRAN_AMOUNT;
    }

    public String getTRAN_AMOUNT() {
        if (TRAN_AMOUNT == null || TRAN_AMOUNT.trim() == "") {
            return "0";
        }
        return TRAN_AMOUNT;
    }

    public void setTRAN_AMOUNT(String TRAN_AMOUNT) {
        this.TRAN_AMOUNT = TRAN_AMOUNT;
    }
}
