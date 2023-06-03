import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposeDestinationsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                val composeDestinations = "1.8.42-beta"
                "implementation"("io.github.raamcosta.compose-destinations:core:$composeDestinations")
                "ksp"("io.github.raamcosta.compose-destinations:ksp:$composeDestinations")
            }
        }
    }
}