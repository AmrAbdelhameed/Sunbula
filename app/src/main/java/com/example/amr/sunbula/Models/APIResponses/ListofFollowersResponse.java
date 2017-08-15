package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 15/08/2017.
 */

public class ListofFollowersResponse {

    /**
     * ListOFFollowers : [{"Name":"mo3taz","FollowID":"5d8b584f-9e96-42de-b353-74e818c6a5fd"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<ListOFFollowersBean> ListOFFollowers;

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

    public List<ListOFFollowersBean> getListOFFollowers() {
        return ListOFFollowers;
    }

    public void setListOFFollowers(List<ListOFFollowersBean> ListOFFollowers) {
        this.ListOFFollowers = ListOFFollowers;
    }

    public static class ListOFFollowersBean {
        /**
         * Name : mo3taz
         * FollowID : 5d8b584f-9e96-42de-b353-74e818c6a5fd
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
