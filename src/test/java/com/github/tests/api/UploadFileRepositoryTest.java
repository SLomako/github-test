package com.github.tests.api;

import com.github.data.TestData;
import com.github.tests.api.managers.RepositoryManager;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.tests.api.models.PutFileContentsRepositoryRequest;
import com.github.utils.Base64Converter;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tests.api.specs.Specification.*;

@Owner("SLomako")
@Feature("Загрузка файла в репозиторий")
@DisplayName("API: Загрузка файла в репозиторий")
public class UploadFileRepositoryTest {

    private final Base64Converter base64Converter = new Base64Converter();
    private final RepositoryManager repositoryManager = new RepositoryManager();
    private final TestData testData = new TestData();

    @Test
    @DisplayName("Успешная загрузка файла")
    void uploadFileTest() {
        String repositoryName = repositoryManager.createRepository();
        String endpoint = String.format("repos/%s/%s/contents/%s", testData.getOwnerName(), repositoryName, testData.getNameNewFile());


        step("Подготовка данных для загрузки файла", () ->
                step("Загрузка файла в репозиторий", () -> {
                    PutFileContentsRepositoryRequest putFileContentsRepositoryRequest = new PutFileContentsRepositoryRequest();
                    putFileContentsRepositoryRequest.setMessage(testData.getMessage());
                    putFileContentsRepositoryRequest.setContent(base64Converter.encodeToBase64(testData.getContent()));

                    String response = given(getRequestSpec())
                            .body(putFileContentsRepositoryRequest)
                            .when()
                            .put(endpoint)
                            .then()
                            .spec(getResponseSpec())
                            .spec(getCreated201Spec())
                            .extract().asString();

                    step("Проверка информации о загруженном файле", () -> {
                        String contentName = JsonPath.from(response).getString("content.name");
                        String commitMessage = JsonPath.from(response).getString("commit.message");

                        assertThat(contentName).isEqualTo(testData.getNameNewFile());
                        assertThat(commitMessage).isEqualTo(testData.getMessage());
                    });
                })
        );

        step("Удаление репозитория", () ->
                repositoryManager.deleteRepository(repositoryName)
        );
    }
}
