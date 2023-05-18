import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensibility.DefaultExtraPropertiesExtension
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.kapt")
            }

            val ext = rootProject.extensions.getByName("ext") as DefaultExtraPropertiesExtension
            val hiltVersion = ext["hilt_version"]

            dependencies {
                add("implementation", "com.google.dagger:hilt-android:$hiltVersion")
                add("kapt", "com.google.dagger:hilt-android-compiler:$hiltVersion")
            }
        }
    }
}