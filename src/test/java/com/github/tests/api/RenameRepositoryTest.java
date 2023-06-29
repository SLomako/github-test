package com.github.tests.api;

import com.github.tests.api.managers.RepositoryManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.data.TestData;
import com.github.tests.api.models.PatchRepositoryRequest;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tests.api.specs.Specification.*;

@Owner("SLomako")
@Epic("Переименование репозитория")
@Feature("API: Переименование репозитория")
@DisplayName("Переименование репозитория")
public class RenameRepositoryTest {

    private final RepositoryManager repositoryManager = new RepositoryManager();
    private final TestData testData = new TestData();

    @Test
    @DisplayName("Успешное переименование репозитория")
    void renameRepository() {
        step("Создание репозитория", () -> {
            String repositoryName = repositoryManager.createRepository();
            String renameEndPoint = String.format("repos/%s/%s", testData.getOwnerName(), repositoryName);

            step("Подготовка данных для переименования репозитория", () ->
                    step("Отправка запроса на переименование репозитория", () -> {
                        PatchRepositoryRequest patchRepositoryRequest = new PatchRepositoryRequest();
                        patchRepositoryRequest.setName(testData.getNewNameRepository());
                        patchRepositoryRequest.setDescription(testData.getDescriptionRepository());

                        Response response = given()
                                .spec(getRequestSpec())
                                .body(patchRepositoryRequest)
                                .when()
                                .patch(renameEndPoint)
                                .then()
                                .spec(getResponseSpec())
                                .spec(getOk200Spec())
                                .extract().response();

                        String actualRepositoryName = patchRepositoryRequest.getName();

                        step("Проверка успешного переименования репозитория", () -> {
                            assertThat(response.statusCode()).isEqualTo(200);
                            assertThat(response.jsonPath().getString("name")).isEqualTo(patchRepositoryRequest.getName());
                            assertThat(response.jsonPath().getString("owner.login")).isEqualTo(testData.getOwnerName());
                            assertThat(response.jsonPath().getString("description")).isEqualTo(patchRepositoryRequest.getDescription());
                        });

                        step("Удаление репозитория", () ->
                                repositoryManager.deleteRepository(actualRepositoryName)
                        );
                    })
            );
        });
    }
}
