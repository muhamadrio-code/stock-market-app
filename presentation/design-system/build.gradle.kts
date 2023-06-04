plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.library.compose")
}

android {
    namespace = "com.riopermana.design_system"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:testing"))

    api(platform("androidx.compose:compose-bom:2022.10.00"))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")
    api("androidx.compose.foundation:foundation")
    api("androidx.compose.foundation:foundation-layout")
    debugApi("androidx.compose.ui:ui-tooling")

//    implementation("androidx.paging:paging-compose:1.0.0-alpha19")
//    implementation 'androidx.compose.material:material-icons-extended'
//    implementation("androidx.compose.foundation:foundation:1.4.3")
//    implementation("androidx.paging:paging-compose:1.0.0-alpha19")
//
    // compose nav
//    val navVersion = "2.5.3"
//    implementation("androidx.navigation:navigation-compose:$navVersion")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

//    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
}