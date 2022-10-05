plugins {
    id(ProjectPlugins.ANDROID_APPLICATION)
    id(ProjectPlugins.JETBRAINS_ANDROID)
    id(ProjectPlugins.HILT_GRADLE)
    id(ProjectPlugins.SECRET_GRADLE)
    id(ProjectPlugins.GOOGLE_SERVICES)
    kotlin(ProjectPlugins.KAPT)
}

android {
    compileSdk = BuildConfig.COMPILE_SDK

    defaultConfig {
        applicationId = BuildConfig.APPLICATION_ID
        minSdk = BuildConfig.MIN_SDK
        targetSdk = BuildConfig.TARGET_SDK
        versionCode = BuildConfig.VERSION_CODE
        versionName = BuildConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Versions.COMPOSE_VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementationDependencies(Modules.APP)
    implementation(project(Modules.CORE_DATA))
    implementation(project(Modules.CORE_DATABASE))
    implementation(project(Modules.COMMON_UI))
    implementation(project(Modules.COMMON_UTIL))
    implementation(project(Modules.COMMON_MODEL))
    implementation(project(Modules.FEATURE_CAMERA))
}

kapt {
    correctErrorTypes = true
}

secrets {
    // To add your Maps API key to this project:
    // 1. Add this line to your local.properties file, where YOUR_API_KEY is your API key:
    //        MAPS_API_KEY=YOUR_API_KEY
    defaultPropertiesFileName = "local.properties"
}