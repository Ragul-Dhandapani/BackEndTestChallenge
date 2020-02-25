package com.freenow.utils;

import com.freenow.constants.RequestType;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.config;

/**
 * @author Ragul Dhandapani
 */
public class ReusableHelper {

    private RequestSpecification requestSpecification;

    public ReusableHelper () {
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 100000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 100000));

        requestSpecification= RestAssured.given ().config (config);
        requestSpecification.header("Content-Type" , "application/json");
    }

    public ValidatableResponse prepareAndSendRequest(RequestType requestType,String baseURL) throws Exception{

        requestSpecification = requestSpecification
                .when().log ().all ();
        return executeAPI (requestType,baseURL);
    }

    public ValidatableResponse prepareAndSendRequest(RequestType requestType,String baseURL, String requestBody)
            throws Exception{

        requestSpecification = requestSpecification
                .body (requestBody)
                .when ().log ().all ();
        return executeAPI (requestType,baseURL);
    }

    public ValidatableResponse prepareAndSendRequest(RequestType requestType, String baseURL,
                                                     Map<String,Object> queryParam,
                                                     String requestBody) throws Exception{

            requestSpecification = requestSpecification.queryParams(queryParam)
                    .body (requestBody)
                    .when ().log ().all ();

            return executeAPI (requestType,baseURL);
    }

    public ValidatableResponse prepareAndSendRequest(RequestType requestType, String baseURL,
                                                     Map<String,Object> queryParam) throws Exception{

        requestSpecification = requestSpecification.queryParams(queryParam).when ().log ().all ();

        return executeAPI (requestType,baseURL);
    }

    public ValidatableResponse executeAPI(RequestType requestType, String baseURL) throws Exception{

        ValidatableResponse response=null;
//        requestSpecification.baseUri (baseURL); //Setting up the base URI before fire the request

        switch(requestType)
        {
            case GET:
                response = requestSpecification.get (baseURL).then ();
                break;
            case PUT:
                response = requestSpecification.put ().then ();
                break;
            case POST:
                response = requestSpecification.post ().then ();
                break;
            case PATCH:
                response = requestSpecification.patch ().then ();
                break;
            case DELETE:
                response = requestSpecification.delete ().then ();
                break;
            default:
                throw new UnsupportedOperationException("Request type is not supported.");

        }

        return response.log ().all ();
    }


    public void validateResponseStatus(ValidatableResponse response,int expectedStatusCode, String expectedStatusLine){

        response.assertThat ().statusCode (expectedStatusCode);
        response.assertThat ().statusLine (expectedStatusLine);

    }

}
