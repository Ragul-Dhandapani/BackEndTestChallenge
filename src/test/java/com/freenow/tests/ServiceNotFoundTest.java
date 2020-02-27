package com.freenow.tests;

import com.freenow.constants.PropertyConstant;
import com.freenow.constants.RequestType;
import com.freenow.suite.Hooks;
import com.freenow.utils.ReusableHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * This Class is used to test the API's in the form of Negative Scenario with "Service Not Found (SNF-404)"
 *
 * @author Ragul Dhandapani
 */
public class ServiceNotFoundTest extends Hooks {

    /**
     * Endpoint Declaration
     *
     */
    private String GET_SNF_USER_INFO = PropertyConstant.baseURL+"/usr";
    private String GET_SNF_USER_ALL_POST_INFO = PropertyConstant.baseURL+"/pst?userId=3";
    private String GET_SNF_USER_ALL_POST_COMMENTS_INFO = PropertyConstant.baseURL+"/posts/21/commnt";

    /**
     * Hits the UserInformation endpoint with misspelled to retrieve the Posts from the Blog
     *
     * Expected: 404 (SNF)
     *
     * @throws Exception
     */
    @Test
    public void getUserInformation()throws  Exception{

        extentTest = extentReports.startTest ("Fetch All Users Information to get ");
        extentTest.setDescription ("Hits the UserInformation endpoint with misspelled to retrieve " +
                "the Posts from the Blog");
        ValidatableResponse response = prepareAndSendRequest (RequestType.GET,GET_SNF_USER_INFO);
        validateResponseStatus (response, HttpStatus.SC_NOT_FOUND,"HTTP/1.1 404 Not Found");

        extentReports.endTest (extentTest);
    }

    /**
     * Hits the UserPostsInformation endpoint with misspelled to retrieve the Posts from the Blog
     *
     * Expected: 404 (SNF)
     *
     * @throws Exception
     */

    @Test
    public void getUserPostsInformation()throws  Exception{

        extentTest = extentReports.startTest ("Fetch All Users Blog Posts Information");
        extentTest.setDescription ("Hits the UserPostsInformation endpoint with misspelled to retrieve " +
                "the Posts from the Blog");

        ValidatableResponse response = prepareAndSendRequest (RequestType.GET,GET_SNF_USER_ALL_POST_INFO);
        validateResponseStatus (response, HttpStatus.SC_NOT_FOUND,"HTTP/1.1 404 Not Found");

        extentReports.endTest (extentTest);
    }

    /**
     * Hits the UserCommentsInformation endpoint with misspelled to retrieve the Posts from the Blog
     *
     * Expected: 404 (SNF)
     *
     * @throws Exception
     */
    @Test
    public void getUserBlogPostCommentsInformation()throws  Exception{

        extentTest = extentReports.startTest ("Fetch All Users Blog Posts Comments Information");
        extentTest.setDescription ("Hits the UserCommentsInformation endpoint with misspelled " +
                "to retrieve the Posts from the Blog");

        ValidatableResponse response = prepareAndSendRequest (RequestType.GET,GET_SNF_USER_ALL_POST_COMMENTS_INFO);
        validateResponseStatus (response, HttpStatus.SC_NOT_FOUND,"HTTP/1.1 404 Not Found");

        extentReports.endTest (extentTest);
    }


}
