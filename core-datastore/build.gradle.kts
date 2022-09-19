plugins {
    id(ProjectPlugins.HILT_GRADLE)
    id(ProjectPlugins.BASE_PLUGIN)
}

dependencies {
    implementationDependencies(Modules.CORE_DATASTORE)
    implementation(project(Modules.COMMON_MODEL))
    implementation(project(Modules.COMMON_UTIL))
}

kapt {
    correctErrorTypes = true
}