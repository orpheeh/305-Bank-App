package xyz.norlib.bank305.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.data.RegistrationData
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.integration.BsaAuthentication
import xyz.norlib.bank305.integration.BsaUserInformation
import xyz.norlib.bank305.ui.components.InAppAuthentication
import xyz.norlib.bank305.ui.components.Loader

@Composable
fun ProfileScreen(onDeleteButtonClicked: () -> Unit, onBack: () -> Unit) {
    var loading by remember { mutableStateOf(false) }
    var registrationData by remember {
        mutableStateOf(
            RegistrationData(
                user = User(userKey = "", name = "", phone = "", email = ""),
                authType = "", disposeToken = ""
            )
        )
    }

    BsaUserInformation.deviceCheck {
        registrationData = it
        loading = false
    }

    if (loading) {
        Loader()
    } else {
        ProfileMain(
            onDeleteButtonClicked = onDeleteButtonClicked,
            onBack = onBack,
            registerData = registrationData
        )
    }

}

@Composable
fun ProfileMain(
    registerData: RegistrationData,
    onDeleteButtonClicked: () -> Unit,
    onBack: () -> Unit
) {

    var authDeleteLoading by remember {
        mutableStateOf(false)
    }
    var authUnRegisterLoading by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(registerData.user.name, style = AppTypography.titleLarge)
                Text(
                    "User key: " + registerData.user.userKey,
                    style = AppTypography.bodyLarge,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
        ProfileDetail(registerData)
        Spacer(modifier = Modifier.size(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!authDeleteLoading && !authUnRegisterLoading) {
                Button(onClick = {
                    Log.d("BSA TOKEN", BsaAuthentication.checkAccessToken())
                    authDeleteLoading = true
                }) {
                    Text("Delete account")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    Log.d("BSA TOKEN", BsaAuthentication.checkAccessToken())
                    authUnRegisterLoading = true
                }) {
                    Text("Unregister Device")
                }
            }

            if (authDeleteLoading) {
                InAppAuthentication(loading = authDeleteLoading, onSuccess = {
                    authDeleteLoading = false
                    BsaUserInformation.deleteUser() {
                        onDeleteButtonClicked()
                    }
                }) {
                    authDeleteLoading = false
                }
            }

            if (authUnRegisterLoading) {
                InAppAuthentication(loading = authUnRegisterLoading, onSuccess = {
                    authUnRegisterLoading = false
                    BsaUserInformation.unregisterDevice(registerData.user.userKey) {
                        onDeleteButtonClicked()
                    }
                }) {
                    authDeleteLoading = false
                }
            }
        }
    }
}

@Composable
fun ProfileDetail(registrationData: RegistrationData) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Email: " + registrationData.user.email)
            Spacer(modifier = Modifier.size(16.dp))
            Text("Phone: " + registrationData.user.phone)
            Spacer(modifier = Modifier.size(16.dp))
            Text("Auth Type: " + registrationData.authType)
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen({}, {})
}