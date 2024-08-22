package xyz.norlib.bank305

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.compose.AppTheme
import com.fnsv.bsa.sdk.BsaSdk
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import xyz.norlib.bank305.integration.BSA_API_URL
import xyz.norlib.bank305.integration.BSA_CLIENT_KEY


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BsaSdk.getInstance().init(
            application.applicationContext,
            BSA_CLIENT_KEY,
            BSA_API_URL
        )
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Bank305App()
            }
        }
    }
}
