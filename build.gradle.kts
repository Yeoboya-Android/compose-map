buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Google.ANDROID_BUILD_GRADLE)
        classpath(Libs.JetBrains.KOTLIN_GRADLE)
        classpath(Libs.Hilt.HILT_GRADLE_PLUGIN)
        classpath(Libs.Google.SECRET_GRADLE_PLUGIN)
        classpath(Libs.Google.GOOGLE_GMS_SERVICE)
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
    delete("$rootDir/build")
}