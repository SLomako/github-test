package ru.lomakosv.tests.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.lomakosv.data.TestData;
import ru.lomakosv.tests.api.models.CreationRepositoryRequest;

import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class ApiTestBase {

    protected ApiSpecification apiSpecification;
    protected TestData testData;
    protected String repositoryName;

    @BeforeEach
    void createRepository() {
        apiSpecification = new ApiSpecification();
        testData = new TestData();

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

        repositoryName = createResponse.getName();
        System.out.println("Создаем репозиторий с именем: " + repositoryName);
    }

    @AfterEach
    void deleteRepository() {
        given(apiSpecification.getRequestSpec())
                .when()
                .delete(String.format("repos/%s/%s", testData.getOwnerName(), repositoryName))
                .then()
                .statusCode(204);
        System.out.println("Удаляем репозиторий с именем: " + repositoryName);
    }
}
