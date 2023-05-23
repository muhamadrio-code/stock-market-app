plugins {
    `kotlin-dsl`
}

group = "com.riopermana.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.0.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "riopermana.stockmarket.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
    plugins {
        register("androidApplication") {
            id = "riopermana.stockmarket.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
    plugins {
        register("androidLibrary") {
            id = "riopermana.stockmarket.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}