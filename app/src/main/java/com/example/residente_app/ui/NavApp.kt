package com.example.residente_app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.residente_app.ui.components.Menuitem
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.residente_app.data.store.TokenStore
import com.example.residente_app.ui.layout.AuthenticatedLayout
import com.example.residente_app.ui.layout.UnauthenticatedLayout
import com.example.residente_app.ui.screens.HomeScreen
import com.example.residente_app.ui.screens.LoginScreen
import com.example.residente_app.ui.screens.ProfileScreen
import com.example.residente_app.ui.screens.admin.AdminHomePage
import com.example.residente_app.ui.screens.user.UserHomePage
import com.example.residente_app.viewmodel.LoginViewModel
import com.example.residente_app.viewmodel.UserViewModel
import android.content.Context
import android.util.Log
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

val userRoutes: List<Menuitem> = listOf(
    Menuitem(
        id = "home",
        title = "Iniciosadsa",
        icon = Icons.Default.Home
    ),
    Menuitem(
        id = "profile",
        title = "Perfil",
        Icons.Default.Person
    ),
    Menuitem(
        id = "settings",
        title = "Configuración",
        icon = Icons.Default.Settings
    )
)

val adminRoutes: List<Menuitem> = listOf(
    Menuitem(
        id = "home",
        title = "Inicio",
        icon = Icons.Default.Home
    ),
    Menuitem(
        id = "profile",
        title = "Perfil",
        Icons.Default.Person
    ),
    Menuitem(
        id = "settings",
        title = "Configuración",
        icon = Icons.Default.Settings
    ),
    Menuitem(
        id = "dashboard",
        title = "Panel de Control",
        icon = Icons.Default.Dashboard
    )
)

object Routes {
    const val LOGIN = "login"
    const val PROFILE = "profile"
    const val USER_HOME = "user/home"
    const val ADMIN_HOME = "admin/home"
    const val HOME = "home"
}
@Composable
fun AppNavigation(vm: LoginViewModel, userVm: UserViewModel, context:Context){

    val nav = rememberNavController()
    val accessToken by userVm.accessToken.collectAsState(initial = null)
    val isStaff by userVm.isStaff.collectAsState(initial = false)
    val isAdmin by userVm.isAdmin.collectAsState(initial = false)
    val sessionLoaded by userVm.sessionLoaded.collectAsState();

    LaunchedEffect(accessToken,isAdmin,isStaff) {

        if(!sessionLoaded) return@LaunchedEffect

        Log.d("nav","TOKEN: ${accessToken},admin: ${isAdmin}, staff: ${isStaff}")

        if(accessToken.isNullOrEmpty()){
            nav.navigate("login"){popUpTo(0)}
        }else{
            if(isAdmin){
                nav.navigate("admin/home")
            }else{
                nav.navigate("user/home")
            }
        }
    }

    NavHost(nav, startDestination = Routes.HOME){
        composable("home"){
            UnauthenticatedLayout{
                HomeScreen(
                    onLogin = { nav.navigate("Login")}
                )
            }
        }
        composable("profile"){
            AuthenticatedLayout(
                items = userRoutes,
                onItemClick = {
                    nav.navigate(it.title)
                }
            ) {
                ProfileScreen(userVm=userVm,
                    onLogoutSuccess = {nav.navigate("home")})
            }
        }
        composable("login"){
            UnauthenticatedLayout {
                LoginScreen(
                    vm,
                    userVm = userVm,
                    onSuccess = {},
                    redirectTo = { nav.navigate(Routes.HOME)}
                )
            }
        }
        composable(route="admin/home"){
            AuthenticatedLayout(
                items = adminRoutes,
                onItemClick = {
                    nav.navigate(it.title)
                }
            ){
                AdminHomePage(userVm)
            }
        }
        composable ("user/home"){
            AuthenticatedLayout(
                items = userRoutes,
                onItemClick = {
                    nav.navigate(it.title)
                }
            ) {
                UserHomePage(userVm)
            }
        }
    }
}