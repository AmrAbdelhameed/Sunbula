package com.example.amr.sunbula.Models.DBFlowWrappers;

import com.example.amr.sunbula.Models.DBFlowModels.AllCausesProfile;

public class AllCausesProfileWrapper {

    private long id;
    private String OwnderID;
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
    private boolean isSelected;

    public AllCausesProfileWrapper(AllCausesProfile allCausesProfile) {

        this.id = allCausesProfile.getId();
        this.OwnderID = allCausesProfile.getOwnderID();
        this.CaseName = allCausesProfile.getCaseName();
        this.CaseDescription = allCausesProfile.getCaseDescription();
        this.CauseID = allCausesProfile.getCauseID();
        this.Amount = allCausesProfile.getAmount();
        this.EndDate = allCausesProfile.getEndDate();
        this.IMG = allCausesProfile.getIMG();
        this.status = allCausesProfile.getStatus();
        this.Numberofjoins = allCausesProfile.getNumberofjoins();
        this.IsJoined = allCausesProfile.isJoined();
        this.IsOwner = allCausesProfile.isOwner();
        this.isSelected = false;
    }

    public String getOwnderID() {
        return OwnderID;
    }

    public void setOwnderID(String ownderID) {
        OwnderID = ownderID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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
