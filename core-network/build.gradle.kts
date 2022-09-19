plugins {
    id(ProjectPlugins.HILT_GRADLE)
    id(ProjectPlugins.BASE_PLUGIN)
}

dependencies {
    implementationDependencies(Modules.CORE_NETWORK)
    implementation(project(Modules.COMMON_MODEL))
}

kapt {
    correctErrorTypes = true
}