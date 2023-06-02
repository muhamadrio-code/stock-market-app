plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.hilt")
    id("riopermana.stockmarket.co.unit-test")
}

android {
    namespace = "com.riopermana.data"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
}