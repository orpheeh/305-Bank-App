package xyz.norlib.bank305.integration

import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.common.SdkConstant
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
        user: User,
        onSuccess: (SendOtpResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        val params: HashMap<String, Any> = HashMap()
        params["clientKey"] = BSA_CLIENT_KEY
        params["userKey"] = user.userKey
        params["name"] = user.name
        params["verifyType"] = SdkConstant.OtpType.EMAIL.value
        params["verifyData"] = user.email
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
        params["phoneNum"] = user.phone
        params["email"] = user.email
        params["authType"] = authType
        params["otpType"] = "email" // or for SMS it's "{sms}"
        params["disposeToken"] = disposeToken
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            params["token"] = token
        }
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