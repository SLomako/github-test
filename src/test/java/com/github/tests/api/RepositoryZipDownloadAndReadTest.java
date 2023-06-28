package com.github.tests.api;

import com.github.data.TestData;
import com.github.tests.api.managers.RepositoryManager;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.utils.ApiZipFileManager;

import static com.github.tests.api.specs.Specification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Feature("Чтение и Скачивание содержимого репозитория")
@DisplayName("API: Чтение и Скачивание содержимого репозитория")
public class RepositoryZipDownloadAndReadTest {

    private final ApiZipFileManager apiZipFileManager = new ApiZipFileManager();
    private final RepositoryManager repositoryManager = new RepositoryManager();
    private final TestData testData = new TestData();

    private String repositoryName;

    @BeforeEach
    void createRepos() {
        step("Создание репозитория", () ->
            repositoryName = repositoryManager.createRepository()
        );
    }

    @AfterEach
    void deleteRepos() {
        step("Удаление репозитория", () ->
            repositoryManager.deleteRepository(repositoryName)
        );
    }

    @Test
    @DisplayName("Скачивание и чтение содержимого файла README из ZIP-архива")
    void testDownloadAndReadFileContentFromZipReadme() {
        String endpoint = String.format("repos/%s/%s/zipball/main", testData.getOwnerName(), repositoryName);

        step("Загрузка ZIP-архива репозитория", () ->
                step("Чтение содержимого файла README из ZIP-архива", () -> {
                    Response response = given()
                            .spec(getRequestSpec())
                            .when()
                            .get(endpoint)
                            .then()
                            .spec(getOk200Spec())
                            .spec(getResponseSpec())
                            .extract().response();

                    String fileContent = apiZipFileManager.readFileContentFromZip(response, "README.md");

                    step("Проверка содержимого файла README", () -> {
                        assertThat(fileContent)
                                .isNotNull()
                                .contains(repositoryName);
                    });
                })
        );
    }

    @Test
    @DisplayName("Скачивание и чтение содержимого несуществующего файла из ZIP-архива")
    void testDownloadAndReadNonexistentFileFromZip() {
        String endpoint = String.format("repos/%s/%s/zipball/main", testData.getOwnerName(), repositoryName);

        step("Загрузка ZIP-архива репозитория", () ->
                step("Чтение содержимого несуществующего файла из ZIP-архива", () -> {
                    Response response = given()
                            .spec(getRequestSpec())
                            .when()
                            .get(endpoint)
                            .then()
                            .spec(getOk200Spec())
                            .spec(getResponseSpec())
                            .extract()
                            .response();

                    String fileContent = apiZipFileManager.readFileContentFromZip(response, "nonexistent-file.txt");

                    step("Проверка содержимого несуществующего файла", () -> {
                        assertThat(fileContent).isEqualTo("Файл nonexistent-file.txt не найден в ZIP-архиве");
                    });
                })
        );
    }
}
