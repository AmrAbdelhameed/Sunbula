package com.example.amr.sunbula.Models.DBFlowWrappers;

import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;

public class HisCausesPeopleWrapper {

    public HisCausesPeopleWrapper(UserDetailsResponse.MyCasesBean myCasesBean) {

        this.CaseName = myCasesBean.getCaseName();
        this.CaseDescription = myCasesBean.getCaseDescription();
        this.CauseID = myCasesBean.getCauseID();
        this.Amount = myCasesBean.getAmount();
        this.EndDate = myCasesBean.getEndDate();
        this.IMG = myCasesBean.getIMG();
        this.status = myCasesBean.getStatus();
        this.Numberofjoins = myCasesBean.getNumberofjoins();
        this.IsJoined = myCasesBean.isIsJoined();
        this.IsOwner = myCasesBean.isIsOwner();
        this.isSelected = false;
    }

    private String CaseName;

    private String CaseDescription;

    private String CauseID;

    private int Amount;

    private String EndDate;

    private String IMG;

    private int status;

    private int Numberofjoins;

    private boolean IsJoined;

    private boolean IsOwner;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getCaseDescription() {
        return CaseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        CaseDescription = caseDescription;
    }

    public String getCauseID() {
        return CauseID;
    }

    public void setCauseID(String causeID) {
        CauseID = causeID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberofjoins() {
        return Numberofjoins;
    }

    public void setNumberofjoins(int numberofjoins) {
        Numberofjoins = numberofjoins;
    }

    public boolean isJoined() {
        return IsJoined;
    }

    public void setJoined(boolean joined) {
        IsJoined = joined;
    }

    public boolean isOwner() {
        return IsOwner;
    }

    public void setOwner(boolean owner) {
        IsOwner = owner;
    }
}
