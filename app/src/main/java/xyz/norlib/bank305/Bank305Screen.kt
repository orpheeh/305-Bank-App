package xyz.norlib.bank305

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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