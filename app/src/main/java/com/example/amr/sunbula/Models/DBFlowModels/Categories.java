package com.example.amr.sunbula.Models.DBFlowModels;

import com.example.amr.sunbula.DBFlow.AppDatabaseSunbula;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabaseSunbula.class)
public class Categories extends BaseModel {

    public Categories() {
    }

    @Column
    @Unique(unique = true)
    @PrimaryKey(autoincrement = true)
    private long id; // package-private recommended, not required

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CategoryName;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String DateCreated;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CategoryDescription;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String CategoryID;

    @Column
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String AllCauses;

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getAllCauses() {
        return AllCauses;
    }

    public void setAllCauses(String allCauses) {
        AllCauses = allCauses;
    }
}
