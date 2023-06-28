package com.github.tests.api.managers;

import com.github.data.TestData;
import com.github.tests.api.models.CreationRepositoryRequest;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static com.github.tests.api.specs.Specification.getRequestSpec;

public class RepositoryManager {

    private final TestData testData = new TestData();
    private final String deleteEndpoint = String.format("repos/%s/", testData.getOwnerName());

    @Step("Создание репозитория со случайным названием")
    public String createRepository() {
        CreationRepositoryRequest createResponse = new CreationRepositoryRequest();
        createResponse.setName(testData.getRepositoryName());
        createResponse.setDescription(testData.getDescriptionRepository());
        createResponse.setAutoInit(testData.getAutoInit());

        String createEndpoint = "user/repos";
        given(getRequestSpec())
                .body(createResponse)
                .when()
                .post(createEndpoint)
                .then()
                .statusCode(201);

        String repositoryName = createResponse.getName();
        System.out.println("Создаем репозиторий с именем: " + repositoryName);

        return repositoryName;
    }

    @Step("Удаление репозитория с именем: {repositoryName}")
    public void deleteRepository(String repositoryName) {
        given(getRequestSpec())
                .when()
                .delete(deleteEndpoint + repositoryName)
                .then()
                .statusCode(204);
        System.out.println("Удаляем репозиторий с именем: " + repositoryName);
    }
}