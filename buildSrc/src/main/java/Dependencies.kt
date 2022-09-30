import Libs.Versions.ANDROID_GRADLE_VERSION
import Libs.Versions.COMPOSE_VERSION
import Libs.Versions.COROUTINE_VERSION
import Libs.Versions.GOOGLE_MAP_VERSION
import Libs.Versions.HILT_VERSION
import Libs.Versions.KOTLIN_VERSION
import Libs.Versions.OK_HTTP4_VERSION
import Libs.Versions.RETROFIT_VERSION
import Libs.Versions.ROOM_VERSION

object Libs {

    /** 버전 정보 */
    object Versions {
        const val COMPOSE_VERSION = "1.2.0"
        const val KOTLIN_VERSION = "1.7.0"
        const val ANDROID_GRADLE_VERSION = "7.2.2"
        const val COROUTINE_VERSION = "1.6.4"
        const val RETROFIT_VERSION = "2.9.0"
        const val OK_HTTP4_VERSION = "4.10.0"
        const val ROOM_VERSION = "2.4.3"
        const val HILT_VERSION = "2.44"
        const val GOOGLE_MAP_VERSION = "2.7.0"
    }

    object Hilt {

        /** 힐트 */
        const val HILT_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$HILT_VERSION"
        internal const val HILT_ANDROID = "com.google.dagger:hilt-android:$HILT_VERSION"
        internal const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
    }

    /** Jetpack 라이브러리 */
    internal object Androidx {

        /** 컴포즈 */
        const val COMPOSE_UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:$COMPOSE_VERSION"
        const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:1.6.0"
        const val COMPOSE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val COMPOSE_MANIFEST = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
        const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"
        const val COMPOSE_RUNTIME = "androidx.compose.runtime:runtime:$COMPOSE_VERSION"
        const val COMPOSE_COMPILER = "androidx.compose.compiler:compiler:1.3.1"
        const val COMPOSE_SYSTEM_UI = "com.google.accompanist:accompanist-systemuicontroller:0.17.0"

        /** 페이징 */
        const val COMPOSE_PAGING = "androidx.paging:paging-compose:1.0.0-alpha16"
        const val COMMON_PAGING = "androidx.paging:paging-runtime-ktx:3.1.1"

        /** DataBase */
        const val ROOM = "androidx.room:room-ktx:$ROOM_VERSION"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
        const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"

        const val PERMISSION_APPCOMPAT = "androidx.appcompat:appcompat:1.5.1"
        const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        const val CORE = "androidx.core:core-ktx:1.9.0"

        /** 테스트 라이브러리 */
        const val JUNIT = "androidx.test.ext:junit:1.1.3"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    }

    internal object Coil {
        const val COIL = "io.coil-kt:coil-compose:2.2.1"
    }

    internal object NetWork {
        const val OKHTTP4 = "com.squareup.okhttp3:okhttp:$OK_HTTP4_VERSION"
        const val RETROFIT2 = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
        const val CONVERTER_SCALARS = "com.squareup.retrofit2:converter-scalars:$RETROFIT_VERSION"
        const val ADAPTER_COROUTINE = "tech.thdev:flow-call-adapter-factory:1.0.0"
    }

    object JetBrains {
        internal const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE_VERSION"
        internal const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINE_VERSION"
        internal const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINE_VERSION"
        const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    }

    object Google {
        internal const val GSON = "com.google.code.gson:gson:2.8.6"
        const val ANDROID_BUILD_GRADLE = "com.android.tools.build:gradle:$ANDROID_GRADLE_VERSION"
        const val GOOGLE_MAP_COMPOSE = "com.google.maps.android:maps-compose:$GOOGLE_MAP_VERSION"
        const val GOOGLE_MAP_COMPOSE_WIDGET = "com.google.maps.android:maps-compose-widgets:$GOOGLE_MAP_VERSION"
        const val GOOGLE_MAP_UTIL = "com.google.maps.android:android-maps-utils:2.3.0"
        const val PLAY_SERVICES_MAP = "com.google.android.gms:play-services-maps:18.1.0"
        const val PLAY_SERVICES_LOCATION = "com.google.android.gms:play-services-location:20.0.0"
        const val PERMISSION = "com.google.accompanist:accompanist-permissions:0.25.1"
        const val SECRET_GRADLE_PLUGIN = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1"
    }

    object Naver {
        internal const val NAVER_MAP_COMPOSE = "io.github.fornewid:naver-map-compose:1.2.1"
        internal const val NAVER_MAP = "com.naver.maps:map-sdk:3.16.0"
    }

    object Etc {
        const val JEXCELAPI = "net.sourceforge.jexcelapi:jxl:2.6.12"
    }

    /** 테스트 라이브러리 */
    internal object Test {
        const val JUNIT = "junit:junit:4.13.2"
    }
}