plugins {
    id("base-script-plugin")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Versions.COMPOSE_VERSION
    }
}