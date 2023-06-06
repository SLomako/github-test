package ru.lomakosv.utils;

import com.codeborne.selenide.Configuration;

public class PathScreenShot {

    public static String getExpectedScreenshotPath(String screenshotName) {
        if (Configuration.remote != null && !Configuration.remote.isEmpty()) {
            return "src/test/resources/screenshottest/remote_" + screenshotName + ".png";
        } else {

            return "src/test/resources/screenshottest/local_" + screenshotName + ".png";
        }
    }

    public static String getActualScreenshotPath(String screenshotName) {

        return "src/test/resources/screenshottest/actual_" + screenshotName + ".png";
    }

    public static String getDiffImagePath(String screenshotName) {

        return "src/test/resources/screenshottest/diff_" + screenshotName + ".png";
    }
}
