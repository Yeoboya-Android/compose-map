plugins {
    id(ProjectPlugins.BASE_PLUGIN)
    id(ProjectPlugins.PARCELIZE)
}

dependencies {
    implementationDependencies(Modules.COMMON_MODEL)
}