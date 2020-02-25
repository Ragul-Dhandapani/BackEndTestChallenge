package com.freenow.tests;

import com.freenow.constants.PropertyConstant;
import com.freenow.constants.RequestType;
import com.freenow.utils.ReusableHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * This Class is used to test the API's in the form of Negative Scenario with "Bad Request"
 *
 *  FYI - Bad Request Cannot be Tested for the below endPoint , Since there are no Request headers & query
 *          parameters for this endpoint to recreate the "BAD Request" Scenario
 *                       Endpoint --> https://jsonplaceholder.typicode.com/users
 *
 * @author Ragul Dhandapani
 */
public class BadRequestTest extends ReusableHelper {

    //BAD --> BAD REQUEST (400)
    /**
     * Endpoints Declaration
     */
    private String GET_BAD_USER_ALL_POST_INFO = PropertyConstant.baseURL+"/posts?userId=3.02";
    private String GET_BAD_USER_ALL_POST_COMMENTS_INFO = PropertyConstant.baseURL+"/posts/%postId%/comments";

    /**
     * Hits the UserPostInformation endpoint with invalid "userId" to retrieve the Posts from the Blog
     *
     * Exepcted: 400 (BAD Request)
     *
     * @throws Exception
     */
    @Test
    public void getUserPostsInformation()throws  Exception{

        ValidatableResponse response = prepareAndSendRequest (RequestType.GET,GET_BAD_USER_ALL_POST_INFO);
        validateResponseStatus (response, HttpStatus.SC_BAD_REQUEST,"HTTP/1.1 400 Bad Request");
    }

    /**
     * Hits the UserCommentsInformation endpoint with invalid "postId" to retrieve the Posts from the Blog
     *
     * Exepcted: 400 (BAD Request)
     *
     * @throws Exception
     */
    @Test
    public void getUserBlogPostCommentsInformation()throws  Exception{

        String endPoint = GET_BAD_USER_ALL_POST_COMMENTS_INFO.replace ("%postId%",String.valueOf (30001011));
        ValidatableResponse response = prepareAndSendRequest (RequestType.GET,endPoint);
        validateResponseStatus (response, HttpStatus.SC_BAD_REQUEST,"HTTP/1.1 400 Bad Request");
    }
}
