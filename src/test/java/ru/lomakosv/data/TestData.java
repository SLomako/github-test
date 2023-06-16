package ru.lomakosv.data;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class TestData {
    private final String
            ownerName = System.getProperty("ownerName", "SLomako"),
            repoUnderTest = System.getProperty("repoUnderTest", "SLomako/github-tester"),
            searchRepo = System.getProperty("searchRepo", "SLomako/github-tester"),
            searchResult = System.getProperty("searchResult", "SLomako/github-tester"),
            advancedSearchLanguage = System.getProperty("advancedSearchLanguage", "Java"),
            advancedSearchFrom = System.getProperty("advancedSearchFrom", "Slomako"),
            advancedSearchDate = System.getProperty("advancedSearchDate", "2023"),
            urlSearchAdvanced = "https://github.com/search/advanced",
            urlLoginPage = "https://github.com/login",
            repositoryName = "API",
            descriptionRepository = "Тест создание репозитория через API",
            targetFileName = "README.md",
            negativeTargetFileName = "invalid_file.txt",
            pageTitle = "GitHub: Let’s build from here · GitHub";

    private final Boolean autoInit = true;

    private final Faker faker = new Faker();
    private final String
            fakerNameNewFile = faker.app().name() + ".txt",
            fakerRepositoryName = faker.lorem().word(),
            fakerNewNameRepository = faker.lorem().word(),
            fakerDescriptionRepository = faker.name().title(),
            fakerMessage = faker.rockBand().name(),
            fakerContent = faker.dog().name();
}

