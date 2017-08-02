package com.example.amr.sunbula.Models.DBFlowModels;

import com.example.amr.sunbula.DBFlow.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class NewsFeed extends BaseModel {

    public NewsFeed() {
    }

    @Column
    @Unique(unique = true)
    @PrimaryKey(autoincrement = true)
    long id; // package-private recommended, not required

    @Column
    @NotNull
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String name;

    @Column
    @NotNull
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String description;

    @Column
    @NotNull
    @Unique(unique = false, uniqueGroups = 1, onUniqueConflict = ConflictAction.REPLACE)
    private String imageurl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
