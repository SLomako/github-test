package ru.lomakosv.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.lomakosv.data.TestData;
import ru.lomakosv.tests.api.models.CreationRepositoryErrorResponse;
import ru.lomakosv.tests.api.models.CreationRepositoryRequest;
import ru.lomakosv.tests.api.models.CreationRepositoryResponse;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Feature("API-тестирование")
@DisplayName("API: Создание репозитория")
@Execution(ExecutionMode.CONCURRENT)
public class CreationRepositoryTest {

    private ApiSpecification apiSpecification;
    private TestData testData;
    protected String repositoryName;

    @BeforeEach
    void setUpTest() {
        apiSpecification = new ApiSpecification();
        testData = new TestData();
    }

    @DisplayName("Тест: Создание репозитория")
    @Test
    void testCreateRepository() {
        CreationRepositoryRequest createRequest = new CreationRepositoryRequest();
        createRequest.setName(testData.getFakerRepositoryName());
        createRequest.setDescription(testData.getDescriptionRepository());
        createRequest.setAutoInit(testData.getAutoInit());

        CreationRepositoryResponse response = given(apiSpecification.getRequestSpec())
                .body(createRequest)
                .when()
                .post("user/repos")
                .then()
                .spec(apiSpecification.getResponseSpec())
                .statusCode(201)
                .extract().as(CreationRepositoryResponse.class);
        repositoryName = createRequest.getName();

        assertThat(response.getName()).isEqualTo(createRequest.getName());
        assertThat(response.getIsPrivate()).isFalse();
        assertThat(response.getOwner().getLogin()).isEqualTo(testData.getOwnerName());
        assertThat(response.getOwner().getUrl()).isEqualTo(String.format("https://api.github.com/users/%s", testData.getOwnerName()));

        given(apiSpecification.getRequestSpec())
                .when()
                .delete(String.format("repos/%s/%s", testData.getOwnerName(), repositoryName))
                .then()
                .spec(apiSpecification.getResponseSpec())
                .statusCode(204);

    }

    @DisplayName("Тест: Создание репозитория с уже существующим именем")
    @Test
    void testCreateRepositoryWithExistingName() {
        CreationRepositoryRequest createResponse = new CreationRepositoryRequest();
        createResponse.setName("GitHub_Tester");
        createResponse.setDescription(testData.getDescriptionRepository());
        createResponse.setAutoInit(testData.getAutoInit());

        CreationRepositoryErrorResponse response = given(apiSpecification.getRequestSpec())
                .body(createResponse)
                .when()
                .post("user/repos")
                .then()
                .spec(apiSpecification.getResponseSpec())
                .statusCode(422).extract().as(CreationRepositoryErrorResponse.class);

        assertThat(response.getErrors().get(0).getMessage()).isEqualTo("name already exists on this account");
        assertThat(response.getMessage()).isEqualTo("Repository creation failed.");
    }
}
