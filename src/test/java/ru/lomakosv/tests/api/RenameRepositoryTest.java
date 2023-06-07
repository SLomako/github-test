package ru.lomakosv.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lomakosv.data.TestData;
import ru.lomakosv.tests.api.model.PatchRepositoryRequest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("API-тестирование")
@DisplayName("API: Переименование репозитория")
public class RenameRepositoryTest extends ApiTestBase {

    private ApiSpecification apiSpecification;
    private TestData testData;

    @BeforeEach
    void setUpTest(){
        apiSpecification = new ApiSpecification();
        testData = new TestData();
    }

    @Owner("SLomako")
    @DisplayName("Тест: Успешное переименование репозитория")
    @Test
    void renameRepository() {
        PatchRepositoryRequest patchRepositoryRequest = new PatchRepositoryRequest();
        patchRepositoryRequest.setName(testData.getFakerNewNameRepository());
        patchRepositoryRequest.setDescription(testData.getFakerDescriptionRepository());

        Response response = given(apiSpecification.getRequestSpec())
                .body(patchRepositoryRequest)
                .when()
                .patch(String.format("repos/%s/%s", testData.getOwnerName(), repositoryName))
                .then()
                .spec(apiSpecification.getResponseSpec()).extract().response();
        repositoryName = patchRepositoryRequest.getName();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("name")).isEqualTo(patchRepositoryRequest.getName());
        assertThat(response.jsonPath().getString("owner.login")).isEqualTo(testData.getOwnerName());
        assertThat(response.jsonPath().getString("description")).isEqualTo(patchRepositoryRequest.getDescription());
    }
}
