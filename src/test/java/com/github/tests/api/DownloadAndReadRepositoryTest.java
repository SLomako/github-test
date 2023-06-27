package com.github.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.data.TestData;
import com.github.utils.ApiZipFileManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Feature("API-тестирование")
@DisplayName("API: Чтение и Скачивание содержимого репозитория")
public class DownloadAndReadRepositoryTest extends ApiTestBase {

    private ApiSpecification apiSpecification;
    private TestData testData;
    private ApiZipFileManager apiZipFileManager;

    @BeforeEach
    void setUpTest() {
        apiSpecification = new ApiSpecification();
        testData = new TestData();
        apiZipFileManager = new ApiZipFileManager();
    }

    @DisplayName("Тест: Скачивание и чтение содержимого файла README из ZIP-архива")
    @Test
    void testDownloadAndReadFileContentFromZipReadme() throws IOException {
        Response response = given(apiSpecification.getRequestSpec())
                .when()
                .get(String.format("repos/%s/%s/zipball/main", testData.getOwnerName(), repositoryName))
                .then()
                .statusCode(200)
                .spec(apiSpecification.getResponseSpec()).extract().response();

        String fileContent = apiZipFileManager.readFileContentFromZip(response, "README.md");

        assertThat(fileContent)
                .isNotNull()
                .contains(repositoryName);
    }

    @DisplayName("Тест: Скачивание и чтение содержимого несуществующего файла из ZIP-архива")
    @Test
    void testDownloadAndReadNonexistentFileFromZip() throws IOException {
        Response response = given(apiSpecification.getRequestSpec())
                .when()
                .get(String.format("repos/%s/%s/zipball/main", testData.getOwnerName(), repositoryName))
                .then()
                .statusCode(200)
                .spec(apiSpecification.getResponseSpec())
                .extract()
                .response();

        String fileContent = apiZipFileManager.readFileContentFromZip(response, "nonexistent-file.txt");
        assertThat(fileContent).isEqualTo("Файл nonexistent-file.txt не найден в ZIP-архиве");
    }

    @DisplayName("Тест: Список репозиториев пользователя")
    @Test
    void testListOfUserRepositories() {
        Response response = given(apiSpecification.getRequestSpec())
                .when()
                .get("users/SLomako/repos")
                .then()
                .statusCode(200)
                .spec(apiSpecification.getResponseSpec())
                .extract()
                .response();

        List<Map<String, ?>> owners = response.path("findAll { it.owner }.owner");
        assertThat(owners).extracting("login").containsOnly("SLomako");
    }
}
