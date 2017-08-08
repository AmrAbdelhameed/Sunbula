package com.example.amr.sunbula.Models.DBFlowWrappers;

import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;

public class MyCausesProfileWrapper {

    public MyCausesProfileWrapper(MyCausesProfile myCausesProfile) {

        this.id = myCausesProfile.getId();
        this.CaseName = myCausesProfile.getCaseName();
        this.CaseDescription = myCausesProfile.getCaseDescription();
        this.CauseID = myCausesProfile.getCauseID();
        this.Amount = myCausesProfile.getAmount();
        this.EndDate = myCausesProfile.getEndDate();
        this.IMG = myCausesProfile.getIMG();
        this.status = myCausesProfile.getStatus();
        this.Numberofjoins = myCausesProfile.getNumberofjoins();
        this.IsJoined = myCausesProfile.isJoined();
        this.IsOwner = myCausesProfile.isOwner();
        this.isSelected = false;
    }

    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
