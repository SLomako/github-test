package ru.lomakosv.utils;

import com.codeborne.selenide.Configuration;

public class PathScreenShot {

    public String getExpectedScreenshotPath(String screenshotName) {
        if (Configuration.remote != null && !Configuration.remote.isEmpty()) {
            return "src/test/resources/screenshottest/remote_" + screenshotName + ".png";
        } else {

            return "src/test/resources/screenshottest/local_" + screenshotName + ".png";
        }
    }

    public String getActualScreenshotPath(String screenshotName) {

        return "src/test/resources/screenshottest/actual_" + screenshotName + ".png";
    }

    public String getDiffImagePath(String screenshotName) {

        return "src/test/resources/screenshottest/diff_" + screenshotName + ".png";
    }
}
