package com.freenow.tests;

import com.freenow.constants.PropertyConstant;
import com.freenow.constants.RequestType;
import com.freenow.entity.AllPostIdInformationBean;
import com.freenow.entity.UserDetailsBean;
import com.freenow.utils.ReusableHelper;
import com.freenow.utils.ValidateUserEmail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.freenow.constants.PropertyConstant.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This Class is used to get one User(Given) Blog Posts comments and validate the other user email addresses who has
 * commented in the same Blog Post.
 *
 *  FYI - You cannot run this class directly , since used @Parameters tag in the @Test Method. Hence recommended
 *  approach is to run the tests from
 *  "testng.xml" file
 *
 * @author Ragul Dhandapani
 */
public class ValidateUserEmailAddress extends ReusableHelper {

    /**
     * Endpoint URI's Declaration
     */
    private String GET_USER_INFO = PropertyConstant.baseURL+"/users";
    private String GET_USER_ALL_POST_INFO = PropertyConstant.baseURL+"/posts?userId=";
    private String GET_USER_ALL_POST_COMMENTS_INFO = PropertyConstant.baseURL+"/posts/%postId%/comments";

    /**
     * Hits UserInformation Endpoint and store the "UserId" in iTestContext(TestNG Session) Variable
     * for easy retrieval in the other "@Tests"
     *
     * @Parameters - Tag is used to get the Particular username from TestNG.xml suite (refer the <Parameter> tag
     * in testng.xml </Parameter>)
     *
     * @param iTestContext
     * @param userName
     * @throws Exception
     */
    @Parameters({ "UserInfo-param" })
    @BeforeClass
    public void fetchUserInformation(ITestContext iTestContext,String userName) throws Exception{

        ValidatableResponse response = prepareAndSendRequest (RequestType.GET, GET_USER_INFO);
       String userId= extractUserIdInformation (response,"$.[?(@.username ==\""+userName+"\")].id");
       iTestContext.setAttribute ("UserId",userId);
       System.out.println ("After UserInformation is fetched --->"+iTestContext.getAttribute ("UserId"));
       validateResponseStatus (response,HttpStatus.SC_OK,HTTP_SC_OK_STATUS_LINE);

    }

    /**
     * Hits UserPostsInformation Endpoint to get all user posts along with postId to retrieve the comments.
     *  and store the Endpoint Response in "Java Bean" (POJO classes) to retrieve easy.
     *
     * @param iTestContext
     * @throws Exception
     */
    @Test(priority=1)
    public void fetchUserPosts(ITestContext iTestContext)throws  Exception{

        System.out.println ("Second Test File--->"+iTestContext.getAttribute ("UserId"));
        Map<String , Object> requestParam=new HashMap<String, Object>();
        requestParam.put ("userId",iTestContext.getAttribute ("UserId"));

        ValidatableResponse response = prepareAndSendRequest (RequestType.GET, GET_USER_ALL_POST_INFO,requestParam);
        validateResponseStatus (response,HttpStatus.SC_OK,HTTP_SC_OK_STATUS_LINE);

        ObjectMapper mapper = new ObjectMapper();
        List<AllPostIdInformationBean> allPostIdInformationList = mapper.readValue(response.extract ().body ().asString (),
                new TypeReference<List<AllPostIdInformationBean>>(){});

        iTestContext.setAttribute ("PostIdInformation",allPostIdInformationList);
    }

    /**
     * Hits UserCommentsInformation Endpoint with help of "PostId" & store the Response into "Java Bean" (POJO classes)
     * then iterate & validate the Blog comments User email address is valid or not.
     *
     * @param iTestContext
     * @throws Exception
     */
    @Test(priority=2)
    public void fetchUserPostCommentsInformation(ITestContext iTestContext)throws  Exception{


        List<AllPostIdInformationBean> informationList = ( List<AllPostIdInformationBean> )
                iTestContext.getAttribute ("PostIdInformation");

        for(AllPostIdInformationBean details: informationList){

         String endPoint=GET_USER_ALL_POST_COMMENTS_INFO.replace("%postId%",details.getId ());
         ValidatableResponse response = prepareAndSendRequest (RequestType.GET, endPoint);
         validateResponseStatus (response,HttpStatus.SC_OK,HTTP_SC_OK_STATUS_LINE);
         ObjectMapper mapper = new ObjectMapper();

        /*CommentsInformation commentsInformation = mapper.readValue(new JSONArray(response.extract ().body ().asString ()),
                                                        CommentsInformation.class);*/

          List<UserDetailsBean> commentsInformation = mapper.readValue(response.extract ().body ().asString (),
                    new TypeReference<List<UserDetailsBean>>(){});

          for( UserDetailsBean userDetailsBean : commentsInformation){
              Assert.assertEquals (true, ValidateUserEmail.verifyEmailId (userDetailsBean.getEmail ()));
            }
        }
    }

    /**
     * Reusable method to retrieve the given JsonPath from the Response Object
     *
     * @param response
     * @param jsonPath
     * @return String
     */
    public String extractUserIdInformation(ValidatableResponse response,String jsonPath){

       JSONArray value=new JSONArray (JsonPath.read(response.extract ().body ().asString (),jsonPath).toString ());
       return value.get (0).toString ();
    }

}
