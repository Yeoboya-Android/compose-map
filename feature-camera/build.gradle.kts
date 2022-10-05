plugins {
    id(ProjectPlugins.COMPOSE_PLUGIN)
    id(ProjectPlugins.HILT_GRADLE)
}

dependencies {
    implementation(project(Modules.COMMON_MODEL))
    implementation(project(Modules.COMMON_UI))
    implementation(project(Modules.COMMON_UTIL))
    implementationDependencies(Modules.FEATURE_CAMERA)
}

kapt {
    correctErrorTypes = true
}