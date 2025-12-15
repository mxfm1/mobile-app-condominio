package com.example.residente_app

import DrawerHeader
import DrawerBody
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.residente_app.ui.theme.AppTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.residente_app.ui.components.AppBar
import com.example.residente_app.ui.theme.ResidenteAppTheme
import com.example.residente_app.ui.AppNavigation
import com.example.residente_app.ui.components.Menuitem
import com.example.residente_app.ui.screens.user.UserHomePage
import com.example.residente_app.viewmodel.AuthViewModelFactory
import com.example.residente_app.viewmodel.InvitationViewModel
import com.example.residente_app.viewmodel.LoginViewModel
import com.example.residente_app.viewmodel.ResidenceViewModel
import com.example.residente_app.viewmodel.UserViewModel
import com.example.residente_app.viewmodel.UsersAppViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vm: LoginViewModel by viewModels {
        AuthViewModelFactory(application)
    }
    private val userVm: UserViewModel by viewModels()
    private val inviteVm: InvitationViewModel by viewModels()
    private val appUserVm: UsersAppViewModel by viewModels()
    private val residenceVm: ResidenceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                AppNavigation(
                    vm, userVm, appUserVm,applicationContext,residenceVm,inviteVm
                )
            }
        }
    }
}


