package com.freenow.entity;

/**
 * This Class acts as "JAVA BEAN" Object to store & retrieve the Response Object Values
 *
 * @author  Ragul Dhandapani
 */
public class AllPostIdInformationBean {

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getBody () {
        return body;
    }

    public void setBody (String body) {
        this.body = body;
    }

    private String userId;
    private String id;
    private String title;
    private String body;
}
