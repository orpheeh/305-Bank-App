package xyz.norlib.bank305

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.AppTypography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.data.fakeUser
import xyz.norlib.bank305.ui.BiometryScreen
import xyz.norlib.bank305.ui.HomeScreen
import xyz.norlib.bank305.ui.LoginScreen
import xyz.norlib.bank305.ui.OTPScreen
import xyz.norlib.bank305.ui.ProfileScreen
import xyz.norlib.bank305.ui.RegisterScreen
import xyz.norlib.bank305.ui.WelcomeScreen
import xyz.norlib.bank305.ui.components.Bank305AppBar
import xyz.norlib.bank305.ui.transaction.AddFund
import xyz.norlib.bank305.ui.transaction.RemoveFund
import xyz.norlib.bank305.ui.transaction.TransferFund

enum class Bank305Screen() {
    Welcome,
    Register,
    Login,
    OTP,
    Biometry,
    Home,
    Profile,
    AddFund,
    RemoveFund,
    TransferFund
}

@Composable
fun Bank305App(
    navController: NavHostController = rememberNavController()
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }
    Scaffold(topBar = {
        Bank305AppBar()
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Bank305Screen.Welcome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Bank305Screen.Welcome.name) {
                WelcomeScreen(
                    modifier = Modifier,
                    onCreateButtonClicked = { navController.navigate(Bank305Screen.Register.name) },
                    onSignInButtonClicked = { navController.navigate(Bank305Screen.Login.name) }
                )
            }
            composable(route = Bank305Screen.OTP.name) {
                OTPScreen(
                    modifier = Modifier,
                    onSendButtonClicked = { navController.navigate(Bank305Screen.Biometry.name) },
                    onResendButtonClicked = { navController.navigate(Bank305Screen.Welcome.name) },
                    user = fakeUser
                )
            }
            composable(route = Bank305Screen.Register.name) {
                RegisterScreen(
                    modifier = Modifier,
                    onCancleButtonClicked = { navController.navigate(Bank305Screen.Login.name) },
                    onCreateButtonClicked = { navController.navigate(Bank305Screen.OTP.name) }
                )
            }
            composable(route = Bank305Screen.Login.name) {
                LoginScreen(
                    modifier = Modifier,
                    onCancleButtonClicked = { navController.navigate(Bank305Screen.Register.name) },
                    onLoginButtonClicked = { navController.navigate(Bank305Screen.OTP.name) }
                )
            }
            composable(route = Bank305Screen.Home.name) {
                HomeScreen(onUserButtonClicked = { navController.navigate(Bank305Screen.Profile.name) },
                    add = { navController.navigate(Bank305Screen.AddFund.name) },
                    remove = { navController.navigate(Bank305Screen.RemoveFund.name) },
                    transfer = { navController.navigate(Bank305Screen.TransferFund.name) })
            }
            composable(route = Bank305Screen.Profile.name) {
                ProfileScreen({}, { navController.popBackStack() })
            }
            composable(route = Bank305Screen.AddFund.name) {
                AddFund()
            }
            composable(route = Bank305Screen.RemoveFund.name) {
                RemoveFund()
            }
            composable(route = Bank305Screen.TransferFund.name) {
                TransferFund()
            }
            composable(route = Bank305Screen.Biometry.name) {
                BiometryScreen(
                    modifier = Modifier,
                    onSendButtonClicked = { navController.navigate(Bank305Screen.Home.name) },
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@Composable
fun RationaleDialog() {
    AlertDialog(onDismissRequest = { },
        confirmButton = {},
        title = { Text("Notification permission", style = AppTypography.titleMedium) },
        text = { Text("The notification permission is important for this app. Please grant permission ") }
    )
}

@Composable
fun PermissionDialog(onPermissionRequestButtonClicked: () -> Unit) {
    AlertDialog(onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = { onPermissionRequestButtonClicked() }
            ) { Text("Confirm") }
        },
        title = { Text("Notification permission", style = AppTypography.titleMedium) },
        text = { Text("The notification permission is important for this app. Please grant permission ") }
    )
}