import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class CoroutineAndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "androidTestImplementation"("junit:junit:4.13.2")
                "androidTestImplementation"("androidx.test.ext:junit:1.1.5")
                "androidTestImplementation"("androidx.test:runner:1.5.2")
                "androidTestImplementation"("androidx.test.espresso:espresso-core:3.5.1")
                "androidTestImplementation"("com.google.truth:truth:1.0.1")
                "androidTestImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

                // mockk version 1.13.3++ currently
                // triggered bug for instrumented test: https://github.com/mockk/mockk/issues/1035
                "androidTestImplementation"("io.mockk:mockk-android:1.13.2")
            }
        }
    }
}