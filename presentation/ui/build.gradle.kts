plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.library.compose")
    id("riopermana.stockmarket.compose-destinations")
}

android {
    namespace = "com.riopermana.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":presentation:design-system"))
}