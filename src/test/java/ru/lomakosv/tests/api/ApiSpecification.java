package ru.lomakosv.tests.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Data;
import ru.lomakosv.config.ApiConfig;
import ru.lomakosv.config.ConfigurationManager;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static ru.lomakosv.helpers.CustomApiListener.withCustomTemplates;

@Data
public class ApiSpecification {

    private ApiConfig apiConfig = ConfigurationManager.getApiConfig();
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private String accessToken = apiConfig.getAccessToken();

    protected ApiSpecification() {
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
