// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    extra.apply {
        set("minSdkAPI", 24)
        set("compileSdkAPI", 34)
        set("targetSdkAPI", 34)
        set("versionCodeNumber", 1)
        set("versionNameText", "1.0")
    }
}