plugins {
    id("riopermana.stockmarket.library")
    id("riopermana.stockmarket.hilt")
}

android {
    namespace = "com.riopermana.sync"

    defaultConfig {
        testInstrumentationRunner = "com.riopermana.testing.StockMarketTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging {
        resources {
            pickFirsts.add("META-INF/LICENSE.md")
            pickFirsts.add("META-INF/LICENSE-notice.md")
        }
    }
}



dependencies {

    androidTestImplementation(project(":core:testing"))
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

    implementation("androidx.tracing:tracing-ktx:1.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")


}