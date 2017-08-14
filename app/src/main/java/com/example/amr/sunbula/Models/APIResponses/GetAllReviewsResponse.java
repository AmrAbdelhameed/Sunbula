package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 14/08/2017.
 */

public class GetAllReviewsResponse {

    /**
     * AllUsersReview : [{"ReviewBody":"good","ReviewStar":"1","ReviewID":"e232f03e-0248-42fb-aa77-030edd1dd2ee","ReviedPerson":"edb9165e-3852-4826-8832-48e928b80321"},{"ReviewBody":"bad person he is a theif","ReviewStar":"1","ReviewID":"18ed5738-3004-4b75-a6e8-61e8b0bd1c37","ReviedPerson":"edb9165e-3852-4826-8832-48e928b80321"},{"ReviewBody":"good","ReviewStar":"4","ReviewID":"69e2a962-98c6-4cd1-aad2-7a43206e8ab3","ReviedPerson":"edb9165e-3852-4826-8832-48e928b80321"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<AllUsersReviewBean> AllUsersReview;

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

    public List<AllUsersReviewBean> getAllUsersReview() {
        return AllUsersReview;
    }

    public void setAllUsersReview(List<AllUsersReviewBean> AllUsersReview) {
        this.AllUsersReview = AllUsersReview;
    }

    public static class AllUsersReviewBean {
        /**
         * ReviewBody : good
         * ReviewStar : 1
         * ReviewID : e232f03e-0248-42fb-aa77-030edd1dd2ee
         * ReviedPerson : edb9165e-3852-4826-8832-48e928b80321
         */

        private String ReviewBody;
        private String ReviewStar;
        private String ReviewID;
        private String ReviedPerson;

        public String getReviewBody() {
            return ReviewBody;
        }

        public void setReviewBody(String ReviewBody) {
            this.ReviewBody = ReviewBody;
        }

        public String getReviewStar() {
            return ReviewStar;
        }

        public void setReviewStar(String ReviewStar) {
            this.ReviewStar = ReviewStar;
        }

        public String getReviewID() {
            return ReviewID;
        }

        public void setReviewID(String ReviewID) {
            this.ReviewID = ReviewID;
        }

        public String getReviedPerson() {
            return ReviedPerson;
        }

        public void setReviedPerson(String ReviedPerson) {
            this.ReviedPerson = ReviedPerson;
        }
    }
}
