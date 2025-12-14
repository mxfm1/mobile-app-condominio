package com.example.residente_app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
import com.example.residente_app.viewmodel.UsersAppViewModel
import android.content.Context
import android.util.Log
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.residente_app.ui.components.layouts.User.UserLayout
import com.example.residente_app.ui.screens.admin.CreateUserScreen
import com.example.residente_app.ui.screens.admin.HouseManageScreen
import com.example.residente_app.ui.screens.admin.UserDetailScreen
import com.example.residente_app.ui.screens.admin.UserListAdminScreen
import com.example.residente_app.viewmodel.ResidenceViewModel
import com.example.residente_app.ui.screens.admin.houses.HouseDetailScreen
import com.example.residente_app.ui.screens.user.CreateInviteScreen
import com.example.residente_app.ui.screens.user.InvitePeopleScreen
import com.example.residente_app.ui.screens.user.UserPackagesScreen
import com.example.residente_app.ui.screens.user.UserProfileScreen


val userRoutes: List<Menuitem> = listOf(
    Menuitem(
        id = "home",
        title = "Iniciosadsa",
        icon = Icons.Default.Home,
        route = "user/home"
    ),
    Menuitem(
        id = "profile",
        title = "Perfil",
        Icons.Default.Person,
        route = "user/profile"
    ),
    Menuitem(
        id = "settings",
        title = "Configuración",
        icon = Icons.Default.Settings,
        route = "user/settings"
    )
)

val adminRoutes: List<Menuitem> = listOf(
    Menuitem(
        id = "home",
        title = "Inicio",
        icon = Icons.Default.Home,
        route = "admin/home"
    ),
    Menuitem(
        id= "domicilio",
        title="Domicilios",
        icon = Icons.Default.Apartment,
        route = "admin/houses"
    ),
    Menuitem(
        id = "settings",
        title = "Configuración",
        icon = Icons.Default.Settings,
        route = "admin/settings"
    ),
    Menuitem(
        id = "dashboard",
        title = "Panel de Control",
        icon = Icons.Default.Dashboard,
        route = "admin/dashboard"
    ),
    Menuitem(
        id="users",
        title = "Ver usuarios",
        icon = Icons.Default.People,
        route = "admin/users/list"
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
fun AppNavigation(
    vm: LoginViewModel,
    userVm: UserViewModel,
    appUserVm: UsersAppViewModel,
    context:Context,
    residenceVm: ResidenceViewModel
){

    val nav = rememberNavController()
    //current route
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val accessToken by userVm.accessToken.collectAsState(initial = null)
    val isStaff by userVm.isStaff.collectAsState(initial = false)
    val isAdmin by userVm.isAdmin.collectAsState(initial = false)
    val sessionLoaded by userVm.sessionLoaded.collectAsState();


    LaunchedEffect(accessToken,isAdmin,isStaff) {

        if(!sessionLoaded) return@LaunchedEffect
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
                   onStart = { nav.navigate("login")}
                )
            }
        }
        composable("profile"){
            AuthenticatedLayout(
                items = userRoutes,
                onItemClick = {
                    nav.navigate(it.route)
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
                    nav.navigate(it.route)
                }
            ){
                AdminHomePage(
                    userVm,
                    userAppVm = appUserVm,
                    residenceVm = residenceVm,
                    onSeeUsers = {
                        nav.navigate("admin/users/list")
                    },
                    onAddUser = {
                        nav.navigate("admin/users/create")
                    },
                    onSeeProperties = {
                        nav.navigate("admin/houses")
                    },
                    onAddProperty = {}
                )
            }
        }

        composable(route="admin/houses"){
            AuthenticatedLayout(
                items = adminRoutes,
                onItemClick = {
                    nav.navigate(it.route)
                }
            ) {
                HouseManageScreen(
                    vm= residenceVm,
                    onHouseRedirect = {identifier ->
                        nav.navigate("admin/houses/$identifier")
                    }
                )
            }
        }

        composable(route="admin/users/create"){
            AuthenticatedLayout(
                items = adminRoutes,
                onItemClick = {
                    nav.navigate(it.route)
                }
            ) {
                CreateUserScreen(
                    vm = appUserVm,
                    onBack = { nav.popBackStack()},
                    onSuccess = { nav.navigate("admin/home")}
                )
            }
        }

        composable(route="admin/users/list"){
            AuthenticatedLayout(
                items = adminRoutes,
                onItemClick = {
                    nav.navigate(it.route)
                }
            ) {
                UserListAdminScreen(
                    onBack = {nav.popBackStack()},
                    vm = appUserVm,
                    onUserClick = {id ->
                        nav.navigate("admin/user/$id")
                    }
                )
            }
        }

        //dinamic routes
        composable(
            route = "admin/user/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            UserDetailScreen(
                userId = id,
                userVm = appUserVm,
                onBack = {nav.popBackStack()}
            )
        }

        composable(
            route = "admin/houses/{identifier}",
            arguments = listOf(navArgument("identifier"){type = NavType.StringType})
        ){ backStackEntry ->

            val identifier = backStackEntry.arguments?.getString("identifier") ?: ""

            HouseDetailScreen(
                residenceVm = residenceVm,
                onBack = {nav.popBackStack()},
                identifier = identifier,
                usersVm = appUserVm,
                onAssign = {}
            )
        }

        /*-----------------------
        UserRoutes
        -------------------------
         */
        composable ("user/home"){
            UserLayout(
                onItemClick = {route ->
                    nav.navigate(route)
                },
                currentRoute=currentRoute
            ) {
                UserHomePage()
            }
        }
        composable ("user/access"){
            UserLayout(
                onItemClick = { route ->
                    nav.navigate(route)
                },
                currentRoute=currentRoute
            ) {
                InvitePeopleScreen(
                    onInviteRedirect = {
                        nav.navigate("user/access/generateInvite")
                    }
                )
            }
        }

        composable("user/access/generateInvite"){
            CreateInviteScreen(
                onScreenBack = {nav.popBackStack()}
            )
        }

        composable("user/packages"){
            UserLayout(
                onItemClick = {route ->
                    nav.navigate(route)
                },
                currentRoute=currentRoute
            ) {
                UserPackagesScreen()
            }
        }
        composable("user/profile"){
            UserLayout(
                onItemClick = {route ->
                    nav.navigate(route)
                },
                currentRoute=currentRoute
            ) {
                UserProfileScreen()
            }
        }
    }
}