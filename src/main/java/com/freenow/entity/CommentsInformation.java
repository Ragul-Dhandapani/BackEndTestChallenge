package com.freenow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

/**
 * This Class acts as "JAVA BEAN" Object to store & retrieve the Response Object Values
 *
 * @author  Ragul Dhandapani
 */
@JsonFormat (shape = JsonFormat.Shape.OBJECT)
public class CommentsInformation {

    public List<UserDetailsBean> getUserDetailsBeanList () {
        return userDetailsBeanList;
    }

    public void setUserDetailsBeanList (List<UserDetailsBean> userDetailsBeanList) {
        this.userDetailsBeanList = userDetailsBeanList;
    }

    private List<UserDetailsBean> userDetailsBeanList;

}
