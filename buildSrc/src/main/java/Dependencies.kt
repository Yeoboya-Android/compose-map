import Libs.Versions.COMPOSE_VERSION
import Libs.Versions.COROUTINE_VERSION
import Libs.Versions.HILT_VERSION
import Libs.Versions.OK_HTTP4_VERSION
import Libs.Versions.RETROFIT_VERSION
import Libs.Versions.ROOM_VERSION
import org.gradle.api.artifacts.dsl.DependencyHandler

object Libs {

    /** 버전 정보 */
    object Versions {
        const val COMPOSE_VERSION = "1.2.0-alpha01"
        const val KOTLIN_VERSION = "1.6.10"
        const val ANDROID_GRADLE_VERSION = "7.2.2"
        const val COROUTINE_VERSION = "1.6.1"
        const val RETROFIT_VERSION = "2.9.0"
        const val OK_HTTP4_VERSION = "4.10.0"
        const val ROOM_VERSION = "2.4.3"
        const val HILT_VERSION = "2.40.5"
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
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:1.5.1"
        const val COMPOSE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val COMPOSE_MANIFEST = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
        const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"

        /** DataBase */
        const val ROOM = "androidx.room:room-ktx:$ROOM_VERSION"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
        const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"

        const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val CORE = "androidx.core:core-ktx:1.7.0"

        /** 테스트 라이브러리 */
        const val JUNIT = "androidx.test.ext:junit:1.1.3"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    }

    internal object Coil {
        const val COIL = "io.coil-kt:coil-compose:2.0.0"
    }

    internal object NetWork {
        const val OKHTTP4 = "com.squareup.okhttp3:okhttp:$OK_HTTP4_VERSION"
        const val RETROFIT2 = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
        const val CONVERTER_SCALARS = "com.squareup.retrofit2:converter-scalars:$RETROFIT_VERSION"
        const val ADAPTER_COROUTINE = "tech.thdev:flow-call-adapter-factory:1.0.0"
    }

    internal object JetBrains {
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE_VERSION"
        const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINE_VERSION"
        const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINE_VERSION"
    }

    internal object Google {
        const val GSON = "com.google.code.gson:gson:2.8.6"
    }

    /** 테스트 라이브러리 */
    internal object Test {
        const val JUNIT = "junit:junit:4.13.2"
    }

    /** app 모듈 의존성 주입 */
    private val appDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 컴포즈 */
            Implementation(Androidx.COMPOSE_ACTIVITY),
            Implementation(Androidx.COMPOSE_VIEW_MODEL),
            AndroidTestImplementation(Androidx.COMPOSE_JUNIT),
            DebugImplementation(Androidx.COMPOSE_UI_TOOLING),
            DebugImplementation(Androidx.COMPOSE_MANIFEST),

            /* 코루틴 */
            Implementation(JetBrains.COROUTINE_ANDROID),
            Implementation(JetBrains.COROUTINE_CORE),

            Implementation(Androidx.LIFECYCLE_RUNTIME),
            Implementation(Androidx.CORE),
            Implementation(Google.GSON),

            AndroidTestImplementation(Androidx.JUNIT),
            AndroidTestImplementation(Androidx.ESPRESSO_CORE),

            TestImplementation(Test.JUNIT)
        )

    /** common-ui 모듈 의존성 주입 */
    private val commonUiDependencies: List<DependencyType>
        get() = listOf(
            /* 컴포즈 */
            Api(Androidx.COMPOSE_UI),
            Api(Androidx.COMPOSE_MATERIAL),
            Api(Androidx.COMPOSE_PREVIEW),
            Api(Coil.COIL),
            AndroidTestImplementation(Androidx.COMPOSE_JUNIT),
            DebugImplementation(Androidx.COMPOSE_UI_TOOLING),
            DebugImplementation(Androidx.COMPOSE_MANIFEST),
        )

    /** common-util 모듈 의존성 주입 */
    private val commonUtilDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 컴포즈 */
            Implementation(Androidx.COMPOSE_ACTIVITY),
        )

    /** core-datastore 모듈 의존성 주입 */
    private val coreDataStoreDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 코루틴 */
            Implementation(JetBrains.COROUTINE_ANDROID),
            Implementation(JetBrains.COROUTINE_CORE),

            Implementation(Google.GSON),
        )

    /** core-network 모듈 의존성 주입 */
    private val coreNetworkDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 코루틴 */
            Implementation(JetBrains.COROUTINE_ANDROID),
            Implementation(JetBrains.COROUTINE_CORE),

            Implementation(NetWork.OKHTTP4),
            Implementation(NetWork.RETROFIT2),
            Implementation(NetWork.CONVERTER_GSON),
            Implementation(NetWork.CONVERTER_SCALARS),
            Implementation(NetWork.ADAPTER_COROUTINE),
        )

    /** core-database 모듈 의존성 주입 */
    private val coreDataBaseDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 코루틴 */
            Implementation(JetBrains.COROUTINE_ANDROID),
            Implementation(JetBrains.COROUTINE_CORE),

            Implementation(Google.GSON),

            Implementation(Androidx.ROOM),
            Implementation(Androidx.ROOM_RUNTIME),
            Kapt(Androidx.ROOM_COMPILER),
        )

    /** core-data 모듈 의존성 주입 */
    private val coreDataDependencies: List<DependencyType>
        get() = listOf(
            /* 힐트 */
            Implementation(Hilt.HILT_ANDROID),
            Kapt(Hilt.HILT_ANDROID_COMPILER),

            /* 코루틴 */
            Implementation(JetBrains.COROUTINE_ANDROID),
            Implementation(JetBrains.COROUTINE_CORE),
        )

    fun DependencyHandler.implementationDependencies(module: String) {
        when (module) {
            Modules.APP -> appDependencies
            Modules.COMMON_UI -> commonUiDependencies
            Modules.COMMON_UTIL -> commonUtilDependencies
            Modules.COMMON_MODEL -> null
            Modules.CORE_DATASTORE -> coreDataStoreDependencies
            Modules.CORE_NETWORK -> coreNetworkDependencies
            Modules.CORE_DATABASE -> coreDataBaseDependencies
            Modules.CORE_DATA -> coreDataDependencies
            else -> null
        }?.onEach { dependencyType ->
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
}

sealed class DependencyType(open val dependency: String)
data class Implementation(override val dependency: String) : DependencyType(dependency)
data class Api(override val dependency: String) : DependencyType(dependency)
data class TestImplementation(override val dependency: String) : DependencyType(dependency)
data class AndroidTestImplementation(override val dependency: String) : DependencyType(dependency)
data class DebugImplementation(override val dependency: String) : DependencyType(dependency)
data class Kapt(override val dependency: String) : DependencyType(dependency)