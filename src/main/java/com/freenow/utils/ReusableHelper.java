package com.freenow.utils;

import com.freenow.constants.RequestType;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
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
     * Variable Declaration
     */
    private RequestSpecification requestSpecification;

    public static ExtentTest extentTest;
    public static ExtentReports extentReports;

    /**
     * This methhod is used to set the configuration & apply a basic headers to hit the endpoints
     */
    public RequestSpecification getRequestSpec(){

          RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 200000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 200000));


       return RestAssured.given ().config (config).relaxedHTTPSValidation().noFilters ()
                .header ("Content-Type" , "application/json");

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


        requestSpecification = getRequestSpec()
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

        requestSpecification = getRequestSpec ()
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


            requestSpecification = getRequestSpec ().queryParams(queryParam)
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

        requestSpecification = getRequestSpec ().queryParams(queryParam).when ().log ().all ();

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
        //53d192
        extentTest.log (LogStatus.INFO,"<b style=color:#9400D3>"+"Endpoint -->"+"</b>"+baseURL);
        extentTest.log (LogStatus.INFO,"<b style=color:#9400D3>"+"Request Type -->"+"</b>"+requestType);
        extentTest.log (LogStatus.INFO,"<b style=color:#66BB6A>"+"Status Line -->"+"</b>"+response.extract ().statusLine ());
        extentTest.log (LogStatus.INFO,"<b style=color:#66BB6A>"+"Status Body -->"+"</b>"
                +response.extract ().statusCode ());
        extentTest.log (LogStatus.INFO,"<b style=color:#9400D3>"+"Response --->"+"</b>"+response.extract ().body ().asString ());
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
