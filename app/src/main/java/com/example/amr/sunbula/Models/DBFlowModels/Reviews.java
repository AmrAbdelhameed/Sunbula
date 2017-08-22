package com.example.amr.sunbula.Models.DBFlowModels;

import com.example.amr.sunbula.DBFlow.AppDatabaseSunbula;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabaseSunbula.class)
public class Reviews extends BaseModel {

    public Reviews() {
    }

    @Column
    @Unique(unique = true)
    @PrimaryKey(autoincrement = true)
    private long id; // package-private recommended, not required

    @Column
    private String ReviewBody;

    @Column
    private String ReviewStar;

    @Column
    private String ReviewID;

    @Column
    private String ReviedPerson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReviewBody() {
        return ReviewBody;
    }

    public void setReviewBody(String reviewBody) {
        ReviewBody = reviewBody;
    }

    public String getReviewStar() {
        return ReviewStar;
    }

    public void setReviewStar(String reviewStar) {
        ReviewStar = reviewStar;
    }

    public String getReviewID() {
        return ReviewID;
    }

    public void setReviewID(String reviewID) {
        ReviewID = reviewID;
    }

    public String getReviedPerson() {
        return ReviedPerson;
    }

    public void setReviedPerson(String reviedPerson) {
        ReviedPerson = reviedPerson;
    }
}
