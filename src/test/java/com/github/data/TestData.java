package com.github.data;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class TestData {
    private final String
            ownerName = System.getProperty("ownerName", "SLomako"),
            repoUnderTest = System.getProperty("repoUnderTest", "SLomako/github-test"),
            searchRepo = System.getProperty("searchRepo", "SLomako/github-test"),
            searchResult = System.getProperty("searchResult", "SLomako/github-test"),
            advancedSearchLanguage = System.getProperty("advancedSearchLanguage", "Java"),
            advancedSearchFrom = System.getProperty("advancedSearchFrom", "Slomako"),
            advancedSearchDate = System.getProperty("advancedSearchDate", "2023"),
            urlSearchAdvanced = "https://github.com/search/advanced",
            urlLoginPage = "https://github.com/login",
            targetFileName = "README.md",
            negativeTargetFileName = "invalid_file.txt",
            pageTitle = "GitHub: Let’s build from here · GitHub";

    private final Boolean autoInit = true;

    private final Faker faker = new Faker();
    private final String
            nameNewFile = faker.app().name() + ".txt",
            repositoryName = faker.lorem().word(),
            newNameRepository = faker.lorem().word(),
            descriptionRepository = faker.name().title(),
            message = faker.rockBand().name(),
            content = faker.dog().name();
}

