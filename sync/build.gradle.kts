plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.hilt")
    id("riopermana.stockmarket.co.android-test")
}

android {
    namespace = "com.riopermana.sync"

    defaultConfig {
        testInstrumentationRunner = "com.riopermana.testing.StockMarketTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}



dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:data"))

    val workVersion = "2.8.1"
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    androidTestImplementation("androidx.work:work-testing:$workVersion")
    implementation("androidx.startup:startup-runtime:1.1.1")

    val hiltExtVersion = "1.0.0"
    implementation("androidx.hilt:hilt-work:$hiltExtVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltExtVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.ext.get("hilt_version")}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${
        rootProject.ext.get("hilt_version")
    }")

    implementation("androidx.tracing:tracing-ktx:1.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")


}