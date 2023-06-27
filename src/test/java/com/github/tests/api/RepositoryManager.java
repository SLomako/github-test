package com.github.tests.api;

import com.github.data.TestData;
import com.github.tests.api.models.CreationRepositoryRequest;

import static io.restassured.RestAssured.given;

public class RepositoryManager {

    private final ApiSpecification apiSpecification;
    private final TestData testData;

    public RepositoryManager() {
        apiSpecification = new ApiSpecification();
        testData = new TestData();
    }

    public String createRepository() {
        CreationRepositoryRequest createResponse = new CreationRepositoryRequest();
        createResponse.setName(testData.getFakerRepositoryName());
        createResponse.setDescription(testData.getDescriptionRepository());
        createResponse.setAutoInit(testData.getAutoInit());

        given(apiSpecification.getRequestSpec())
                .body(createResponse)
                .when()
                .post("user/repos")
                .then()
                .statusCode(201);

        String repositoryName = createResponse.getName();
        System.out.println("Создаем репозиторий с именем: " + repositoryName);

        return repositoryName;
    }

    public void deleteRepository(String repositoryName) {
        given(apiSpecification.getRequestSpec())
                .when()
                .delete(String.format("repos/%s/%s", testData.getOwnerName(), repositoryName))
                .then()
                .statusCode(204);
        System.out.println("Удаляем репозиторий с именем: " + repositoryName);
    }
}
