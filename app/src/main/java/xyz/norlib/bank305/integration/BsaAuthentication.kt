package xyz.norlib.bank305.integration

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkAuthResponseCallback
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.common.SdkUtil
import com.fnsv.bsa.sdk.response.AuthCompleteResponse
import com.fnsv.bsa.sdk.response.AuthExistResponse
import com.fnsv.bsa.sdk.response.AuthResultResponse
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.ReRegisterDeviceResponse

object BsaAuthentication {

    fun checkAccessToken(): String {
        val accessToken = SdkUtil.getAccessToken()
        return accessToken
    }

    fun normalAuthentication(
        userKey: String,
        isAuth: Boolean = true,
        fragmentActivity: FragmentActivity,
        onSuccess: (AuthCompleteResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
        onProcess: () -> Unit
    ) {
        BsaSdk.getInstance().sdkService.normalAuthenticator(userKey, isAuth,
            fragmentActivity, object : SdkAuthResponseCallback<AuthCompleteResponse> {
                override fun onSuccess(result: AuthCompleteResponse?) {
                    onSuccess(result)
                }

                override fun onProcess(p0: Boolean, p1: String?) {
                    onProcess()
                }

                override fun onFailed(errorResult: ErrorResult?) {
                    onFailed(errorResult)
                }
            })
    }

    fun inAppAuthentication(
        userKey: String,
        isAuth: Boolean = true,
        fragmentActivity: FragmentActivity,
        onSuccess: (AuthResultResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
        onProcess: () -> Unit
    ) {
        BsaSdk.getInstance().sdkService.appAuthenticator(userKey, isAuth,
            fragmentActivity, object : SdkAuthResponseCallback<AuthResultResponse> {
                override fun onSuccess(result: AuthResultResponse?) {
                    onSuccess(result)
                }
                override fun onProcess(p0: Boolean, p1: String?) {
                    Log.d("BSA Progress", p0.toString())
                }
                override fun onFailed(errorResult: ErrorResult?) {
                    onFailed(errorResult)
                }
            })

        /*BsaSdk.getInstance().sdkService.existAuth(userKey, object:
            SdkResponseCallback<AuthExistResponse> {
            override fun onSuccess(authExist: AuthExistResponse?) {
            Log.d("BSA EXIST", authExist?.data?.isExist.toString())
            // Code to be executed if there's currently an ongoing authentication
            }
            override fun onFailed(authFailed: ErrorResult?) {
                Log.d("BSA EXIST ERROR", authFailed?.errorCode.toString())
            }
        })*/
    }
}