package com.freenow.tests;

import com.freenow.constants.PropertyConstant;
import com.freenow.utils.ReusableHelper;

public class GetUsersPostInformationTest extends ReusableHelper {

    private String GET_USER_ALL_POST_INFO = PropertyConstant.baseURL+"/posts?userId=";

   /* @Test
    public void getUserPostsInformation(ITestContext iTestContext)throws  Exception{

        System.out.println ("Second Test File--->"+iTestContext.getAttribute ("UserId"));
        Response response = prepareAndSendRequest (RequestType.GET, GET_USER_ALL_POST_INFO+
                iTestContext.getAttribute ("UserId"));


    }*/

}
