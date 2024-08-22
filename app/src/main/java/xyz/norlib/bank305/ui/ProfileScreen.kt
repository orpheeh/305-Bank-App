package xyz.norlib.bank305.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.data.fakeUser

@Composable
fun ProfileScreen(onDeleteButtonClicked: () -> Unit, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(fakeUser.name, style = AppTypography.titleLarge)
                Text(
                    "User key: " + fakeUser.userKey,
                    style = AppTypography.bodyLarge,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
        ProfileDetail()
        Spacer(modifier = Modifier.size(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { onDeleteButtonClicked() }) {
                Text("Delete account")
            }
        }
    }
}

@Composable
fun ProfileDetail() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Email: " + fakeUser.email)
            Spacer(modifier = Modifier.size(16.dp))
            Text("Phone: " + fakeUser.phone)
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen({}, {})
}