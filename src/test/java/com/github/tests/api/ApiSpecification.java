package com.github.tests.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import com.github.config.ApiConfig;
import com.github.config.ConfigurationManager;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static com.github.helpers.CustomApiListener.withCustomTemplates;

public class ApiSpecification {

    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    protected ApiSpecification() {
        ApiConfig apiConfig = ConfigurationManager.getApiConfig();
        String accessToken = apiConfig.getGitHubApiToken();

        requestSpec = with()
                .baseUri("https://api.github.com")
                .header("Authorization", "token " + accessToken)
                .log().all()
                .filter(withCustomTemplates())
                .contentType(JSON);

        responseSpec = new ResponseSpecBuilder()
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    public ResponseSpecification getResponseSpec() {
        return responseSpec;
    }
}
