plugins {
    id(ProjectPlugins.COMPOSE_PLUGIN)
}

dependencies {
    implementationDependencies(Modules.COMMON_UI)
    implementation(project(Modules.COMMON_MODEL))
}
