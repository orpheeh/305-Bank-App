package xyz.norlib.bank305.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.R
import xyz.norlib.bank305.integration.BsaAuthentication

@Composable
fun InAppAuthentication(
    loading: Boolean,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    BsaAuthentication.inAppAuthentication("",
        fragmentActivity = LocalContext.current as FragmentActivity,
        onSuccess = {
            onSuccess()
        },
        onFailed = {
            onFailure()
        }, onProcess = {
        })
    if (loading) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Security, contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = colorResource(id = R.color.purple_200)
                )
                Text(
                    stringResource(id = R.string.in_app_auth_text),
                    style = AppTypography.bodyLarge,
                    color = colorResource(
                        id = R.color.purple_200
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                Loader(size = 24.dp)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }

}

@Composable
@Preview
fun InAppAuthenticationPreview() {
    InAppAuthentication(onSuccess = { }, loading = true) {

    }
}