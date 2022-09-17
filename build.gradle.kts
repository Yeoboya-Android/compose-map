buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath(Libs.Hilt.HILT_GRADLE_PLUGIN)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id(ProjectPlugins.ANDROID_APPLICATION) version(Libs.Versions.ANDROID_GRADLE_VERSION) apply(false)
//    id(ProjectPlugins.ANDROID_LIBRARY) version(Libs.Versions.ANDROID_GRADLE_VERSION) apply(false)
//    id(ProjectPlugins.JETBRAINS_ANDROID) version(Libs.Versions.KOTLIN_VERSION) apply(false)
//}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
    delete("$rootDir/build")
}