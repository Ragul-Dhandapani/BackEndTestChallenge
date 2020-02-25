package com.freenow.utils;

import com.freenow.constants.RequestType;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import java.util.Map;


/**
 * This Class acts as "Helper" with all Reusable methods which can be accessible across the project to Hit the "API's"
 * and returns the Response Object
 *
 * @author Ragul Dhandapani
 */
public class ReusableHelper {

    /**
     * Declaration
     */
    private RequestSpecification requestSpecification;

    /**
     * This Constructor block is used to set the configuration & apply a basic headers to hit the endpoints
     */
    public ReusableHelper () {
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 10000));

        requestSpecification= RestAssured.given ().config (config).relaxedHTTPSValidation().noFilters ();
        requestSpecification.header("Content-Type" , "application/json");
    }

    /**
     * This method is used to prepare the RequestSpecification with given "RequestType" & "BaseURL" and logs the flow
     *
     * @param requestType
     * @param baseURL
     * @return ValidatableResponse
     * @throws Exception
     */
    public ValidatableResponse prepareAndSendRequest(RequestType requestType,String baseURL) throws Exception{

        requestSpecification = requestSpecification
                .when().log ().all ();
        return executeAPI (requestType,baseURL);
    }

    /**
     * This method is used to prepare the RequestSpecification with given "RequestType" & "BaseURL" and logs the flow
     * @param requestType
     * @param baseURL
     * @param requestBody
     * @return ValidatableResponse
     * @throws Exception
     */
    public ValidatableResponse prepareAndSendRequest(RequestType requestType,String baseURL, String requestBody)
            throws Exception{

        requestSpecification = requestSpecification
                .body (requestBody)
                .when ().log ().all ();
        return executeAPI (requestType,baseURL);
    }

    /**
     * This method is used to prepare the RequestSpecification with given "RequestType" & "BaseURL" and logs the flow
     * @param requestType
     * @param baseURL
     * @param queryParam
     * @param requestBody
     * @return ValidatableResponse
     * @throws Exception
     */
    public ValidatableResponse prepareAndSendRequest(RequestType requestType, String baseURL,
                                                     Map<String,Object> queryParam,
                                                     String requestBody) throws Exception{

            requestSpecification = requestSpecification.queryParams(queryParam)
                    .body (requestBody)
                    .when ().log ().all ();

            return executeAPI (requestType,baseURL);
    }

    /**
     * This method is used to prepare the RequestSpecification with given "RequestType" & "BaseURL" and logs the flow
     * @param requestType
     * @param baseURL
     * @param queryParam
     * @return ValidatableResponse
     * @throws Exception
     */
    public ValidatableResponse prepareAndSendRequest(RequestType requestType, String baseURL,
                                                     Map<String,Object> queryParam) throws Exception{

        requestSpecification = requestSpecification.queryParams(queryParam).when ().log ().all ();

        return executeAPI (requestType,baseURL);
    }

    /**
     * Used to Execute the API with given "RequestType" & "BaseURL" and returns the Response Object
     *
     * @param requestType
     * @param baseURL
     * @return ValidatableResponse
     * @throws Exception
     */
    public ValidatableResponse executeAPI(RequestType requestType, String baseURL) throws Exception{

        ValidatableResponse response=null;

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

    /**
     * Assert the Response Object "Status Code" & "Status Reason" to make sure Expected & Actual Results are same
     * @param response
     * @param expectedStatusCode
     * @param expectedStatusLine
     */
    public void validateResponseStatus(ValidatableResponse response,int expectedStatusCode, String expectedStatusLine){

        response.assertThat ().statusCode (expectedStatusCode);
        response.assertThat ().statusLine (expectedStatusLine);

    }

}
