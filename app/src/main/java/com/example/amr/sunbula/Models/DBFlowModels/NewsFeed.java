package com.example.amr.sunbula.Models.DBFlowModels;

import com.example.amr.sunbula.DBFlow.AppDatabaseNewsFeed;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabaseNewsFeed.class)
public class NewsFeed extends BaseModel {

    public NewsFeed() {
    }

    @Column
    @Unique(unique = true)
    @PrimaryKey(autoincrement = true)
    private long id; // package-private recommended, not required

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CaseName;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CaseDescription;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CauseID;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private int Amount;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String EndDate;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String IMG;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private int status;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private int Numberofjoins;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private boolean IsJoined;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private boolean IsOwner;

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
