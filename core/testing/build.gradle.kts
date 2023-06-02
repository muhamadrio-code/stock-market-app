plugins {
    id("riopermana.stockmarket.library")
}

android {
    namespace = "com.riopermana.testing"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":sync"))

    implementation("junit:junit:4.13.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    implementation("androidx.test.ext:junit:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    implementation("com.google.dagger:hilt-android-testing:${rootProject.ext.get("hilt_version")}")
//    api("androidx.test.espresso:espresso-core:3.5.1")
//    api("androidx.compose.ui:ui-test-junit4")

    // mockk version 1.13.3++ currently
    // triggered bug for instrumented test: https://github.com/mockk/mockk/issues/1035
//    api("io.mockk:mockk-android:1.13.2")
//    debugApi("androidx.compose.ui:ui-test-manifest:1.4.3")
}