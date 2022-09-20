pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}
rootProject.name = "compose_map"
include(":app")
include(":core-network")
include(":core-database")
include(":core-data")
include(":common-ui")
include(":common-util")
include(":common-model")
include(":core-datastore")
