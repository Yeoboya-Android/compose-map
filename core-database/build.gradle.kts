plugins {
    id(ProjectPlugins.BASE_PLUGIN)
    id(ProjectPlugins.HILT_GRADLE)
}

dependencies {
    implementationDependencies(Modules.CORE_DATABASE)
    implementation(project(Modules.COMMON_MODEL))
}

kapt {
    correctErrorTypes = true
}