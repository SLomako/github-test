package ru.lomakosv.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lomakosv.data.TestData;
import ru.lomakosv.tests.api.model.PutFileContentsRepositoryRequest;
import ru.lomakosv.utils.Base64Converter;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Feature("API-тестирование")
@DisplayName("API: Загрузка файла в репозиторий")
public class UploadFileRepositoryTest extends ApiTestBase {

    private ApiSpecification apiSpecification;
    private TestData testData;
    private Base64Converter base64Converter;

    @BeforeEach
    void setUpTest() {
        apiSpecification = new ApiSpecification();
        testData = new TestData();
        base64Converter = new Base64Converter();

    }

    @DisplayName("Тест: Успешная загрузка файла")
    @Test
    void UploadFileTest() {
        PutFileContentsRepositoryRequest putFileContentsRepositoryRequest = new PutFileContentsRepositoryRequest();
        putFileContentsRepositoryRequest.setMessage(testData.getFakerMessage());
        putFileContentsRepositoryRequest.setContent(base64Converter.encodeToBase64(testData.getFakerContent()));

        String response = given(apiSpecification.getRequestSpec())
                .body(putFileContentsRepositoryRequest)
                .when()
                .put(String.format("repos/%s/%s/contents/%s", testData.getOwnerName(), repositoryName, testData.getFakerNameNewFile()))
                .then()
                .spec(apiSpecification.getResponseSpec())
                .statusCode(201).extract().asString();

        String contentName = JsonPath.from(response).getString("content.name");
        String commitMessage = JsonPath.from(response).getString("commit.message");

        assertThat(contentName).isEqualTo(testData.getFakerNameNewFile());
        assertThat(commitMessage).isEqualTo(testData.getFakerMessage());
    }
}
