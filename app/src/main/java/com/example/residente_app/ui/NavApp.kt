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
import com.example.residente_app.ui.layout.AuthenticatedLayout
import com.example.residente_app.ui.layout.UnauthenticatedLayout
import com.example.residente_app.ui.screens.HomeScreen
import com.example.residente_app.ui.screens.LoginScreen
import com.example.residente_app.ui.screens.ProfileScreen
import com.example.residente_app.ui.screens.admin.AdminHomePage
import com.example.residente_app.ui.screens.user.UserHomePage
import com.example.residente_app.viewmodel.LoginViewModel
import com.example.residente_app.viewmodel.UserViewModel


val userRoutes: List<Menuitem> = listOf(
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
fun AppNavigation(vm: LoginViewModel, userVm: UserViewModel){

    val nav = rememberNavController()


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
                    onSuccess = {nav.navigate("profile")},
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
                AdminHomePage()
            }
        }
        composable ("user/home"){
            AuthenticatedLayout(
                items = userRoutes,
                onItemClick = {
                    nav.navigate(it.title)
                }
            ) {
                UserHomePage()
            }
        }
    }
}