package com.upp.reverseauction.model;

import java.io.Serializable;
import java.util.Date;

public class ProcurementOffer implements Serializable{

    private boolean isWithdrawal;
    private float charge;
    private Date performDueDate;
    private Company owner;

    public ProcurementOffer() {
    }

    public ProcurementOffer(boolean isWithdrawal, Company owner) {
        this.isWithdrawal = isWithdrawal;
        this.owner = owner;
        if (isWithdrawal) {
            this.charge = 0;
            this.performDueDate = null;
        }
    }

    public ProcurementOffer(float charge, Date performDueDate, Company owner) {
        this.owner = owner;
        this.isWithdrawal = false;
        this.charge = charge;
        this.performDueDate = performDueDate;
    }

    public boolean isWithdrawal() {
        return isWithdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        isWithdrawal = withdrawal;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }

    public Date getPerformDueDate() {
        return performDueDate;
    }

    public void setPerformDueDate(Date performDueDate) {
        this.performDueDate = performDueDate;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company owner) {
        this.owner = owner;
    }
}
