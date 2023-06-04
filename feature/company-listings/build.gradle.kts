plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.library.compose")
    id("riopermana.stockmarket.co.unit-test")
    id("riopermana.stockmarket.compose-destinations")
    id("riopermana.stockmarket.hilt")
}

android {
    namespace = "com.riopermana.company_listings"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:testing"))
    implementation(project(":core:data"))
    implementation(project(":sync"))
    implementation(project(":presentation:ui"))
    implementation(project(":presentation:design-system"))

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}