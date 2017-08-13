package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 14/08/2017.
 */

public class ListofPepoleResponse {

    /**
     * ListofPepoleFollowing : [{"Name":"testmail2","FollowID":"4bab52bc-d354-4a61-9252-b237a48fd8e3"},{"Name":"Ahmed Ammar","FollowID":"1d6b9f5c-5e38-45c3-afbf-5e3d6c0f7451"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<ListofPepoleFollowingBean> ListofPepoleFollowing;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public List<ListofPepoleFollowingBean> getListofPepoleFollowing() {
        return ListofPepoleFollowing;
    }

    public void setListofPepoleFollowing(List<ListofPepoleFollowingBean> ListofPepoleFollowing) {
        this.ListofPepoleFollowing = ListofPepoleFollowing;
    }

    public static class ListofPepoleFollowingBean {
        /**
         * Name : testmail2
         * FollowID : 4bab52bc-d354-4a61-9252-b237a48fd8e3
         */

        private String Name;
        private String FollowID;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getFollowID() {
            return FollowID;
        }

        public void setFollowID(String FollowID) {
            this.FollowID = FollowID;
        }
    }
}
