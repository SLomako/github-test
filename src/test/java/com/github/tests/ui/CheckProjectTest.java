package com.github.tests.ui;

import com.github.data.TestData;
import com.github.utils.ConsecutiveEmptyLines;
import com.github.utils.ContentChecker;
import com.github.utils.UiZipProcessor;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.github.tests.ui.pages.RepositoryActionsPage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Epic("Проверка дипломных проектов qa.guru")
@Feature("UI: Проверка наличия основных компонентов проекта")
public class CheckProjectTest extends UiTestBase {

    private final UiZipProcessor uiZipProcessor = new UiZipProcessor();
    private final TestData testData = new TestData();
    private final RepositoryActionsPage repositoryActionsPage = new RepositoryActionsPage();

    static Stream<List<String>> parameterizedTestData() {
        return Stream.of(
                Arrays.asList("@Owner", "lombok", "owner", "assertj", "get", "post", "delete")
        );
    }

    @ParameterizedTest(name = "ключевых слов {0}")
    @MethodSource("parameterizedTestData")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Поиск в скачанном файле")
    void testSearchInDownloadedFile(List<String> parameterizedTestData) throws IOException {
        repositoryActionsPage.openMainPage(testData.getRepoUnderTest());
        repositoryActionsPage.clickCodeButton();

        File inputZip = repositoryActionsPage.clickDownloadZipButton();
        File outputFile = uiZipProcessor.processZipFile(inputZip);

        step("Проверить содержимое скачанного файла", () -> {
            ContentChecker contentChecker = new ContentChecker(outputFile, parameterizedTestData);
            contentChecker.assertThatAllFound();
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка на избыточные пустые строки подряд")
    void testForEmptyStrings() throws IOException {
        repositoryActionsPage.openMainPage(testData.getRepoUnderTest());
        repositoryActionsPage.clickCodeButton();

        File inputZip = repositoryActionsPage.clickDownloadZipButton();
        File outputFile = uiZipProcessor.processZipFile(inputZip);

        step("Проверить содержимое скачанного файла", () -> {
            ConsecutiveEmptyLines cel = new ConsecutiveEmptyLines();
            boolean isConsecutiveEmptyLinesValid = cel.assertEmptyLines(outputFile, 3);
            assertThat(isConsecutiveEmptyLinesValid).isTrue();
        });
    }
}
