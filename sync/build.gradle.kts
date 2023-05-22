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

    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.startup:startup-runtime:1.1.1")

    val hiltExtVersion = "1.0.0"
    implementation("androidx.hilt:hilt-work:$hiltExtVersion")
    implementation("androidx.hilt:hilt-compiler:$hiltExtVersion")

    implementation("androidx.tracing:tracing-ktx:1.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0-alpha05")
}