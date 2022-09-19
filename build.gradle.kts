buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Google.ANDROID_BUILD_GRADLE)
        classpath(Libs.JetBrains.KOTLIN_GRADLE)
        classpath(Libs.Hilt.HILT_GRADLE_PLUGIN)
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
    delete("$rootDir/build")
}