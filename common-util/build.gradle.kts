plugins {
    id(ProjectPlugins.COMPOSE_PLUGIN)
    id(ProjectPlugins.HILT_GRADLE)
}

dependencies {
    implementation(project(Modules.COMMON_MODEL))
    implementation(project(Modules.COMMON_UI))
    implementationDependencies(Modules.COMMON_UTIL)
}

kapt {
    correctErrorTypes = true
}