package ru.lomakosv.tests.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lomakosv.data.TestData;
import ru.lomakosv.tests.ui.steps.RepositoryActionsSteps;
import ru.lomakosv.utils.ConsecutiveEmptyLines;
import ru.lomakosv.utils.ContentChecker;
import ru.lomakosv.utils.UiZipProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Epic("Проверка комплектности данных проекта")
@Feature("UI-тестирование")
@DisplayName("UI: Проверка наличия основных компонентов проекта")
public class CheckProjectTest extends UiTestBase {

    private UiZipProcessor uiZipProcessor;
    private TestData testData;
    private RepositoryActionsSteps steps;

    @BeforeEach
    void setUpTest() {
        uiZipProcessor = new UiZipProcessor();
        testData = new TestData();
        steps = new RepositoryActionsSteps();
    }

    static Stream<List<String>> parameterizedTestData() {
        return Stream.of(
                Arrays.asList("@Owner", "lombok", "owner", "assertj", "get", "post", "delete")
        );
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Тест: Поиск в скачанном файле")
    @ParameterizedTest(name = "ключевых слов {0}")
    @MethodSource("parameterizedTestData")
    void testSearchInDownloadedFile(List<String> parameterizedTestData) throws IOException {
        steps.openMainPage(testData.getRepoUnderTest());
        steps.clickCodeButton();

        File inputZip = steps.clickDownloadZipButton();
        File outputFile = uiZipProcessor.processZipFile(inputZip);

        step("Проверить содержимое скачанного файла", () -> {
            ContentChecker contentChecker = new ContentChecker(outputFile, parameterizedTestData);
            contentChecker.assertThatAllFound();
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Тест: Проверка на избыточные пустые строки подряд")
    @Test
    void testForEmptyStrings() throws IOException {
        steps.openMainPage(testData.getRepoUnderTest());
        steps.clickCodeButton();

        File inputZip = steps.clickDownloadZipButton();
        File outputFile = uiZipProcessor.processZipFile(inputZip);

        step("Проверить содержимое скачанного файла", () -> {
            ConsecutiveEmptyLines cel = new ConsecutiveEmptyLines();
            boolean isConsecutiveEmptyLinesValid = cel.assertEmptyLines(outputFile, 3);
            assertThat(isConsecutiveEmptyLinesValid).isTrue();
        });
    }
}
