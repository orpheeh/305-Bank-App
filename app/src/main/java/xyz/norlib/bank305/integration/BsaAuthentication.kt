package xyz.norlib.bank305.integration

import androidx.fragment.app.FragmentActivity
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkAuthResponseCallback
import com.fnsv.bsa.sdk.common.SdkUtil
import com.fnsv.bsa.sdk.response.AuthCompleteResponse
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
}