package xyz.norlib.bank305.integration

import androidx.fragment.app.FragmentActivity
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.common.SdkConstant
import com.fnsv.bsa.sdk.response.AuthBiometricResponse
import com.fnsv.bsa.sdk.response.CheckDuplicateEmailOrPhoneNumberReponse
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.RegisterUserResponse
import com.fnsv.bsa.sdk.response.SendOtpResponse
import com.fnsv.bsa.sdk.response.VerifyOtpResponse
import com.google.firebase.messaging.FirebaseMessaging
import xyz.norlib.bank305.data.User

object BsaRegistrationRepository {

    /**
     * Duplicate email or phone check before register user account for the first time
     */
    fun checkDuplicationEmailOrPhone(
        data: String,
        onSuccess: (CheckDuplicateEmailOrPhoneNumberReponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
        type: SdkConstant.OtpType = SdkConstant.OtpType.EMAIL
    ) {
        val params: HashMap<String, String> = HashMap()
        params["verifyType"] = type.name
        params["verifyData"] = data
        BsaSdk.getInstance().sdkService.isDuplicatedEmailOrPhoneNumber(
            params as Map<String, Any>?,
            object :
                SdkResponseCallback<CheckDuplicateEmailOrPhoneNumberReponse> {
                override fun onSuccess(result: CheckDuplicateEmailOrPhoneNumberReponse?) {
                    onSuccess(result)
                }

                override fun onFailed(errorResult: ErrorResult?) {
                    onFailed(errorResult)
                }
            })
    }

    /**
     * Send otp for confirm that the user is the owner of the email when register or authentification request
     */
    fun sendEmailVerificationOtpCode(
        email: String,
        onSuccess: (SendOtpResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        val params: HashMap<String, Any> = HashMap()
        params["clientKey"] = BSA_CLIENT_KEY
        params["email"] = email
        BsaSdk.getInstance().sdkService.sendOtpByEmail(params, object :
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
     * When user receive the otp code, this function is use to verify the validity of otp
     */
    fun emailVerificationConfirmation(
        email: String,
        authNumber: String,
        onSuccess: (VerifyOtpResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        val params: HashMap<String, Any> = HashMap()
        params["clientKey"] = BSA_CLIENT_KEY
        params["email"] = email
        params["authNum"] = authNumber
        BsaSdk.getInstance().sdkService.verifyOtpByEmail(
            params,
            object : SdkResponseCallback<VerifyOtpResponse> {
                override fun onSuccess(result: VerifyOtpResponse?) {
                    onSuccess(result)
                }

                override fun onFailed(errorResult: ErrorResult?) {
                    onFailed(errorResult)
                }
            })
    }


    /**
     * Save the selected local authentification system: Biometry
     */
    fun localAuthentifcation(
        fragmentActivity: FragmentActivity,
        onSuccess: (AuthBiometricResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ) {
        BsaSdk.getInstance().sdkService.registerBiometric(fragmentActivity, object :
            SdkResponseCallback<AuthBiometricResponse> {
            override fun onSuccess(authBiometric: AuthBiometricResponse?) {
                onSuccess(authBiometric)
            }

            override fun onFailed(errorResult: ErrorResult?) {
                onFailed(errorResult)
            }
        })
    }

    /**
     * Register user data
     */
    fun register(
        user: User,
        authType: String,
        disposeToken: String,
        onSuccess: (AuthBiometricResponse?) -> Unit,
        onFailed: (ErrorResult?) -> Unit,
    ){
        val params: MutableMap<String, Any> = HashMap()
        params["userKey"] = user.userKey
        params["name"] = user.name
        params["phoneNum"] = user.phone
        params["email"] = user.email
        params["authType"] = authType
        params["agreeGccs"] = true
        params["agreePerson"] = true
        params["agreeDevice"] = true
        params["disposeToken"] = disposeToken
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            params["token"] = token
        }
        BsaSdk.getInstance().sdkService.registerUser(params, object:
            SdkResponseCallback<RegisterUserResponse> {
            override fun onSuccess(result: RegisterUserResponse?) {
                onSuccess(result)
            }
            override fun onFailed(errorResult: ErrorResult?) {
                onFailed(errorResult)
            }
        })
    }

}