package com.github.tests.api;

import com.github.data.TestData;
import com.github.tests.api.models.CreationRepositoryErrorResponse;
import com.github.tests.api.models.CreationRepositoryRequest;
import com.github.tests.api.models.CreationRepositoryResponse;
import com.github.tests.api.managers.RepositoryManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tests.api.specs.Specification.*;

@Owner("SLomako")
@Epic("Создание репозитория")
@Feature("API: Создание репозитория")
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Создание репозитория")
public class CreateRepositoryTest {

    private final TestData testData = new TestData();
    private final RepositoryManager repositoryManager = new RepositoryManager();
    private final String createEndpoint = "user/repos";
    protected String actualRepositoryName;

    @Test
    @DisplayName("Успешное создание репозитория")
    void testCreateRepository() {
        step("Создание запроса на создание репозитория", () ->
                step("Отправка запроса на создание репозитория", () -> {
                    CreationRepositoryRequest createRequest = new CreationRepositoryRequest();
                    createRequest.setName(testData.getRepositoryName());
                    createRequest.setDescription(testData.getDescriptionRepository());
                    createRequest.setAutoInit(testData.getAutoInit());

                    CreationRepositoryResponse response = given()
                            .spec(getRequestSpec())
                            .body(createRequest)
                            .when()
                            .post(createEndpoint)
                            .then()
                            .spec(getResponseSpec())
                            .spec(getCreated201Spec())
                            .extract().as(CreationRepositoryResponse.class);
                    actualRepositoryName = createRequest.getName();

                    step("Проверка успешного создания репозитория", () -> {
                        assertThat(response.getName()).isEqualTo(createRequest.getName());
                        assertThat(response.getIsPrivate()).isFalse();
                        assertThat(response.getOwner().getLogin()).isEqualTo(testData.getOwnerName());
                        assertThat(response.getOwner().getUrl()).isEqualTo(String.format("https://api.github.com/users/%s", testData.getOwnerName()));
                    });
                })
        );

        step("Удаление созданного репозитория", () ->
                repositoryManager.deleteRepository(actualRepositoryName)
        );
    }

    @Test
    @DisplayName("Создание репозитория с уже существующим именем")
    void testCreateRepositoryWithExistingName() {
        step("Создание запроса на создание репозитория с уже существующим именем", () ->
                step("Отправка запроса на создание репозитория", () -> {
                    CreationRepositoryRequest createResponse = new CreationRepositoryRequest();
                    createResponse.setName("GitHub_Tester");
                    createResponse.setDescription(testData.getDescriptionRepository());
                    createResponse.setAutoInit(testData.getAutoInit());

                    CreationRepositoryErrorResponse response = given()
                            .spec(getRequestSpec())
                            .body(createResponse)
                            .when()
                            .post(createEndpoint)
                            .then()
                            .spec(getResponseSpec())
                            .spec(getValidationFailed422Spec())
                            .extract().as(CreationRepositoryErrorResponse.class);

                    step("Проверка ошибки создания репозитория с уже существующим именем", () -> {
                        assertThat(response.getErrors().get(0).getMessage()).isEqualTo("name already exists on this account");
                        assertThat(response.getMessage()).isEqualTo("Repository creation failed.");
                    });
                })
        );
    }
}
