package com.zohausman.mycandycotton.model;

public class index_response_model {
    String message;
    //constructor
    public index_response_model(String message) {
        this.message = message;
    }
    public index_response_model(){   }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
