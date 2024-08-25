package xyz.norlib.bank305.integration

import android.util.Log
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.TokenResponse
import com.google.firebase.messaging.FirebaseMessaging

fun RegisterFCMToken() {
    //FCM Push Notification Token Registered
    FirebaseMessaging.getInstance().token.addOnSuccessListener {
        Log.d("ABCDEFG", "FirebaseMessaging token : $it")
        try {
            BsaSdk.getInstance().sdkService.registerPushToken(it, object :
                SdkResponseCallback<TokenResponse> {
                override fun onSuccess(result: TokenResponse?) {
                    Log.d("ABCDEFG", "registerPushToken onSuccess : $result")
                }

                override fun onFailed(errorResult: ErrorResult?) {
                    Log.d("ABCDEFG", "registerPushToken onFailed : $errorResult")
                    Log.d(
                        "ABCDEFG",
                        "registerPushToken onFailed rtcode :" + errorResult!!.errorCode
                    )
                    Log.d(
                        "ABCDEFG",
                        "registerPushToken onFailed message: +" + errorResult!!.errorMessage
                    )
                }
            })
        } catch (exception: Exception) {
            // Handle any exceptions that may occur
        }
    }
}
