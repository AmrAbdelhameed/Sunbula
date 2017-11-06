package com.example.amr.sunbula.Models.DBFlowWrappers;

import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;

/**
 * Created by Amr on 07/08/2017.
 */

public class NewsFeedWrapper {

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
    private boolean isExpanded;

    public NewsFeedWrapper(NewsFeed newsFeed) {

        this.id = newsFeed.getId();
        this.OwnderID = newsFeed.getOwnderID();
        this.CaseName = newsFeed.getCaseName();
        this.CaseDescription = newsFeed.getCaseDescription();
        this.CauseID = newsFeed.getCauseID();
        this.Amount = newsFeed.getAmount();
        this.EndDate = newsFeed.getEndDate();
        this.IMG = newsFeed.getIMG();
        this.status = newsFeed.getStatus();
        this.Numberofjoins = newsFeed.getNumberofjoins();
        this.IsJoined = newsFeed.isJoined();
        this.IsOwner = newsFeed.isOwner();
        this.isExpanded = false;
    }

    public String getOwnderID() {
        return OwnderID;
    }

    public void setOwnderID(String ownderID) {
        OwnderID = ownderID;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
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
