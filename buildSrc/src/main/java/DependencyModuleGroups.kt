

/** app 모듈 의존성 주입 */
internal val appDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 컴포즈 */
        Implementation(Libs.Androidx.COMPOSE_ACTIVITY),
        Implementation(Libs.Androidx.COMPOSE_LIFECYCLE_VIEW_MODEL),
        Implementation(Libs.Androidx.COMPOSE_LIFECYCLE_RUNTIME),
        AndroidTestImplementation(Libs.Androidx.COMPOSE_JUNIT),
        DebugImplementation(Libs.Androidx.COMPOSE_UI_TOOLING),
        DebugImplementation(Libs.Androidx.COMPOSE_MANIFEST),
        Implementation(Libs.Androidx.COMPOSE_NAVIGATION),

        Api(Libs.Coil.COIL),

        /* 페이징 */
        Implementation(Libs.Androidx.COMPOSE_PAGING),

        /* 코루틴 */
        Implementation(Libs.JetBrains.COROUTINE_ANDROID),
        Implementation(Libs.JetBrains.COROUTINE_CORE),

        /* 구글 맵 */
        Implementation(Libs.Google.GOOGLE_MAP_COMPOSE),
        Implementation(Libs.Google.GOOGLE_MAP_COMPOSE_WIDGET),
        Implementation(Libs.Google.GOOGLE_MAP_UTIL),

        /* 네이버 맵 */
        Implementation(Libs.Naver.NAVER_MAP),
        Implementation(Libs.Naver.NAVER_MAP_COMPOSE),

        /* 플레이 서비스 */
        Implementation(Libs.Google.PLAY_SERVICES_MAP),
        Implementation(Libs.Google.PLAY_SERVICES_LOCATION),

        Implementation(Libs.Androidx.LIFECYCLE_RUNTIME),
        Implementation(Libs.Androidx.CORE),
        Implementation(Libs.Google.GSON),

        Implementation(Libs.Etc.JEXCELAPI),

        /* 로티 */
        Implementation(Libs.Lottie.LOTTIE_COMPOSE),

        /* 번역 */
        Implementation(Libs.Google.GOOGLE_LANGUAGE),
        Implementation(Libs.Google.GOOGLE_LANGUAGE_TRANSLATE),

        AndroidTestImplementation(Libs.Androidx.JUNIT),
        AndroidTestImplementation(Libs.Androidx.ESPRESSO_CORE),

        TestImplementation(Libs.Test.JUNIT)
    )

/** common-ui 모듈 의존성 주입 */
internal val commonUiDependencies: List<DependencyType>
    get() = listOf(
        /* 컴포즈 */
        Api(Libs.Androidx.COMPOSE_UI),
        Api(Libs.Androidx.COMPOSE_MATERIAL),
        Api(Libs.Androidx.COMPOSE_PREVIEW),
        Api(Libs.Androidx.COMPOSE_RUNTIME),
        Api(Libs.Androidx.COMPOSE_COMPILER),
        Api(Libs.Coil.COIL),
        AndroidTestImplementation(Libs.Androidx.COMPOSE_JUNIT),
        DebugImplementation(Libs.Androidx.COMPOSE_UI_TOOLING),
        DebugImplementation(Libs.Androidx.COMPOSE_MANIFEST),
        Implementation(Libs.Androidx.COMPOSE_SYSTEM_UI),
    )

/** common-util 모듈 의존성 주입 */
internal val commonUtilDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 컴포즈 */
        Implementation(Libs.Androidx.COMPOSE_ACTIVITY),

        /* 페이징 */
        Implementation(Libs.Androidx.COMPOSE_PAGING),

        /* 권한 */
        Implementation(Libs.Google.PERMISSION),
        Implementation(Libs.Androidx.PERMISSION_APPCOMPAT)
    )

/** common-model 모듈 의존성 주입 */
internal val commonModelDependencies : List<DependencyType>
    get() = listOf(
        Implementation(Libs.Google.GSON)
    )

/** core-datastore 모듈 의존성 주입 */
internal val coreDataStoreDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 코루틴 */
        Implementation(Libs.JetBrains.COROUTINE_ANDROID),
        Implementation(Libs.JetBrains.COROUTINE_CORE),

        Implementation(Libs.Google.GSON),
    )

/** core-network 모듈 의존성 주입 */
internal val coreNetworkDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 코루틴 */
        Implementation(Libs.JetBrains.COROUTINE_ANDROID),
        Implementation(Libs.JetBrains.COROUTINE_CORE),

        Implementation(Libs.NetWork.OKHTTP4),
        Implementation(Libs.NetWork.RETROFIT2),
        Implementation(Libs.NetWork.CONVERTER_GSON),
        Implementation(Libs.NetWork.CONVERTER_SCALARS),
        Implementation(Libs.NetWork.ADAPTER_COROUTINE),
    )

/** core-database 모듈 의존성 주입 */
internal val coreDataBaseDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 코루틴 */
        Implementation(Libs.JetBrains.COROUTINE_ANDROID),
        Implementation(Libs.JetBrains.COROUTINE_CORE),

        Implementation(Libs.Google.GSON),

        Implementation(Libs.Androidx.ROOM),
        Implementation(Libs.Androidx.ROOM_RUNTIME),
        Kapt(Libs.Androidx.ROOM_COMPILER),
    )

/** core-data 모듈 의존성 주입 */
internal val coreDataDependencies: List<DependencyType>
    get() = listOf(
        /* 힐트 */
        Implementation(Libs.Hilt.HILT_ANDROID),
        Kapt(Libs.Hilt.HILT_ANDROID_COMPILER),

        /* 페이징*/
        Implementation(Libs.Androidx.COMMON_PAGING),

        /* 코루틴 */
        Implementation(Libs.JetBrains.COROUTINE_ANDROID),
        Implementation(Libs.JetBrains.COROUTINE_CORE),
    )