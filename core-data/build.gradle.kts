plugins {
    id(ProjectPlugins.HILT_GRADLE)
    id(ProjectPlugins.BASE_PLUGIN)
}

dependencies {
    implementation(project(Modules.CORE_DATABASE))
    implementation(project(Modules.CORE_NETWORK))
    implementation(project(Modules.COMMON_MODEL))
    implementation(project(Modules.COMMON_UTIL))
    implementationDependencies(Modules.CORE_DATA)
}

kapt {
    correctErrorTypes = true
}