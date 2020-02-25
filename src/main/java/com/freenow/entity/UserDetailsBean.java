package com.freenow.entity;

/**
 * This Class acts as "JAVA BEAN" Object to store & retrieve the Response Object Values
 *
 * @author  Ragul Dhandapani
 */
public class UserDetailsBean {

    public String getPostId () {
        return postId;
    }

    public void setPostId (String postId) {
        this.postId = postId;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getBody () {
        return body;
    }

    public void setBody (String email) {
        this.body = body;
    }

    
    private String postId;
    
    private String id;
    
    private String name;
    
    private String email;
    
    private String body;

}
