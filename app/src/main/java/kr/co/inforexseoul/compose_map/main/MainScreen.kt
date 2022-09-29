package kr.co.inforexseoul.compose_map.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.compose_map.map.MapScreen
import kr.co.inforexseoul.compose_map.translate.TranslateScreen


private val screens = listOf(
    DrawerScreens.Map,
    DrawerScreens.Translate
)

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val viewModelStoreOwner = LocalViewModelStoreOwner.current
        val scaffoldState = rememberScaffoldState()

        val openDrawer: () -> Unit = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }

        val appbarTitle: MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }

        Scaffold(
            topBar = { MainTopAppBar(appbarTitle.value, openDrawer) },
            scaffoldState = scaffoldState,
            drawerShape = RectangleShape,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                Drawer { route ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = DrawerScreens.Map.route,
            ) {
                composable(DrawerScreens.Map.route) {
                    viewModelStoreOwner?.let {
                        CompositionLocalProvider(LocalViewModelStoreOwner provides it) {
                            MapScreen(appbarTitle = appbarTitle)
                        }
                    }
                }
                composable(DrawerScreens.Translate.route) {
                    viewModelStoreOwner?.let {
                        CompositionLocalProvider(LocalViewModelStoreOwner provides it) {
                            TranslateScreen(appbarTitle = appbarTitle)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainTopAppBar(
    title: @Composable () -> Unit,
    onMenuClick: () -> Unit,
) {
    TopAppBar(
        title = title,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = Color.White,
                    contentDescription = "Menu"
                )
            }
        },
        elevation = 12.dp,
    )
}


@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.bus_station),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = Color.White),
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .background(color = MaterialTheme.colors.primary, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "넣을거 없어서 넣음", modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(80.dp))
        Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
        screens.forEach { screen ->
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .clickable {
                        onDestinationClicked(screen.route)
                    }
            ) {
                Text(
                    text = screen.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            }
            Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
        }
    }
}

sealed class DrawerScreens(val title: String, val route: String) {
    object Map : DrawerScreens("지도", "map")
    object Translate : DrawerScreens("번역", "translate")
}