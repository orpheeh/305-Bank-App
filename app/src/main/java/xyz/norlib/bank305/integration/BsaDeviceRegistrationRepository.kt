package xyz.norlib.bank305.integration

import android.util.Log
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.common.SdkConstant
import com.fnsv.bsa.sdk.common.SdkConstant.AuthTypes
import com.fnsv.bsa.sdk.common.SdkConstant.OtpType
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.ReRegisterDeviceResponse
import com.fnsv.bsa.sdk.response.SendOtpResponse
import com.google.firebase.messaging.FirebaseMessaging
import xyz.norlib.bank305.data.User

object BsaDeviceRegistrationRepository {

    /**
     * User check
     */
    fun userCheckAndOtpDispatch(
        userKey: String,
        name: String,
        onSuccess: (SendOtpResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        val params: HashMap<String, Any> = HashMap()
        params["clientKey"] = BSA_CLIENT_KEY
        params["userKey"] = userKey
        params["name"] = name
        params["verifyType"] = SdkConstant.OtpType.EMAIL.value
        params["verifyData"] = userKey
        BsaSdk.getInstance().sdkService.sendOtpByRegisterDevice(params, object :
            SdkResponseCallback<SendOtpResponse> {
            override fun onSuccess(result: SendOtpResponse?) {
                onSuccess(result)
            }

            override fun onFailed(errorResult: ErrorResult?) {
                onFailed(errorResult)
            }
        })
    }

    /**
     * Device Re-registration
     */
    fun deviceReregistration(
        user: User,
        authType: String,
        disposeToken: String,
        onSuccess: (ReRegisterDeviceResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        val params: MutableMap<String, Any> = HashMap()
        params["userKey"] = user.userKey
        params["name"] = user.name
        params["phoneNum"] = "077598300"
        params["email"] = user.userKey
        params["agreeGccs"] = true
        params["agreePerson"] = true
        params["agreeDevice"] = true
        params["authType"] = AuthTypes.BIOMETRIC.value
        params["otpType"] = OtpType.EMAIL.value // or for SMS it's "{sms}"
        params["disposeToken"] = disposeToken
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            params["token"] = token
            Log.d("BSA Re-registration", params.toString())
            BsaSdk.getInstance().sdkService.reRegisterUserDevice(params, object:
                SdkResponseCallback<ReRegisterDeviceResponse> {
                override fun onSuccess(result: ReRegisterDeviceResponse?) {
                    onSuccess(result)
                }

                override fun onFailed(errorResult: ErrorResult?) {
                    onFailed(errorResult)
                }
            })
        }

    }

}