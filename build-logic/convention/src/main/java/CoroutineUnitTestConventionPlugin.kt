import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class CoroutineUnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            dependencies {
                "testImplementation"("junit:junit:4.13.2")
                "testImplementation"("androidx.test.ext:junit:1.1.5")
                "testImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
                "testImplementation"("com.google.truth:truth:1.0.1")
                "testImplementation"("io.mockk:mockk-android:1.13.2")
            }
        }
    }
}