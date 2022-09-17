plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
    languageVersion = "1.6.10"
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
}