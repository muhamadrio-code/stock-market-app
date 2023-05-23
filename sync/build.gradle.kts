plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.hilt")
}

android {
    namespace = "com.riopermana.sync"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

    implementation(project(":core:testing"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    val workVersion = "2.8.1"
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    androidTestImplementation("androidx.work:work-testing:$workVersion")
    implementation("androidx.startup:startup-runtime:1.1.1")

    val hiltExtVersion = "1.0.0"
    implementation("androidx.hilt:hilt-work:$hiltExtVersion")
    implementation("androidx.hilt:hilt-compiler:$hiltExtVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${
        rootProject.ext.get("hilt_version")
    }")

    implementation("androidx.tracing:tracing-ktx:1.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
}