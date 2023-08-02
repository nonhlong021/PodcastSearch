package org.aluve.services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;

public class Services {

    private RequestSpecification requestSpec;
    private final String baseURL;
    private String endPoint = "";
    private Map<String, String> headers;
    private String body;
    private Map<String, String> requestParam;
    private Map<String, String> formParams;
    private String methods;
    private final String[] acceptableMethods = new String[]{"GET", "POST", "DELETE", "PUT"};
    private Cookie cookie;
    private Cookies cookies;
    private ArrayList<Object> media;

    public Services(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setEndpoint(String endpoint) {
        this.endPoint = endpoint;
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addBody(String body) {
        this.body = body;
    }

    public void addRequestParam(Map<String, String> requestParam) {
        this.requestParam = requestParam;
    }

    public void addFile(String key, String absoluteFileLocation){
        this.media = new ArrayList<Object>(){{add(key); add(new File(absoluteFileLocation));}};
    }

    public void build() {
        requestSpec = new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

    private RequestSpecification request(){
        RequestSpecification requestModifier = given().spec(requestSpec);
        if(headers!=null){
            requestModifier.headers(headers);
        }

        if (formParams !=null){
            requestModifier.formParams(formParams);
        }

        if (formParams!=null){
            requestModifier.formParams(formParams);
        }


        if(requestParam!=null){
            requestModifier.params(requestParam);
        }

        if(body!=null){
            requestModifier.body(body);
        }

        if(cookie!=null){
            requestModifier.cookie(cookie);
        }

        if(cookies!=null){
            requestModifier.cookies(cookies);
        }

        if(media!=null){
            requestModifier.multiPart((String) media.get(0),(File) media.get(1));
        }
        return requestModifier;
    }

    public void addFormParams(Map<String, String> formParams){
        this.formParams = formParams;
    }

    public void setHTTPMethod(String method) {
        if(Arrays.stream(acceptableMethods).anyMatch(Predicate.isEqual(method.toUpperCase()))) {
            this.methods = method.toUpperCase();
        }
        else{
            throw new IllegalArgumentException("Invalid method: " + method
                    +"\nValid methods: " + Arrays.toString(acceptableMethods));
        }
    }


    public String getCookie(){
        return headers.get("Cookie");
    }

    public Response send() {

        switch(methods) {
            case "POST":
                return request().when().redirects().follow(true).post(endPoint);
            case "PUT":
                return request().put(endPoint);
            case "DELETE":
                return request().delete(endPoint);
            default:
                return request().get(endPoint);
        }
    }

    public void setCookie(Cookie cookie) {
        this.cookie=cookie;
    }

    public void setCookies(Cookie cookie1, Cookie cookie2) {
        this.cookies=new Cookies(cookie1, cookie2);
    }

    public void setCookies(Cookies cookies) {
        this.cookies=cookies;
    }
}
