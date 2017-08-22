package com.example.amr.sunbula.Models.DBFlowModels;

import com.example.amr.sunbula.DBFlow.AppDatabaseSunbula;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabaseSunbula.class)
public class Followers extends BaseModel {

    public Followers() {
    }

    @Column
    @Unique(unique = true)
    @PrimaryKey(autoincrement = true)
    private long id; // package-private recommended, not required

    @Column
    private String Name;

    @Column
    private String FollowID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFollowID() {
        return FollowID;
    }

    public void setFollowID(String followID) {
        FollowID = followID;
    }
}
