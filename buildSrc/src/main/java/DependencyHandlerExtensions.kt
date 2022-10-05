import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementationDependencies(module: String) {
    when (module) {
        Modules.APP -> appDependencies
        Modules.COMMON_UI -> commonUiDependencies
        Modules.COMMON_UTIL -> commonUtilDependencies
        Modules.COMMON_MODEL -> commonModelDependencies
        Modules.CORE_DATASTORE -> coreDataStoreDependencies
        Modules.CORE_NETWORK -> coreNetworkDependencies
        Modules.CORE_DATABASE -> coreDataBaseDependencies
        Modules.CORE_DATA -> coreDataDependencies
        Modules.FEATURE_CAMERA -> featureCameraDependencies
        else -> error("Not Found Module: $module")
    }.forEach { dependencyType ->
        val notation = when (dependencyType) {
            is AndroidTestImplementation -> "androidTestImplementation"
            is DebugImplementation -> "debugImplementation"
            is Implementation -> "implementation"
            is Api -> "api"
            is Kapt -> "kapt"
            is TestImplementation -> "testImplementation"
        }
        add(notation, dependencyType.dependency)
    }
}