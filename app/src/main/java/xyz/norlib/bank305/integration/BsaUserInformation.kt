package xyz.norlib.bank305.integration

import android.util.Log
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.response.DeleteUserResponse
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.MeResponse
import com.fnsv.bsa.sdk.response.UnRegisterDeviceResponse
import xyz.norlib.bank305.data.RegistrationData
import xyz.norlib.bank305.data.User

object BsaUserInformation {

    fun unregisterDevice(userKey: String, onSucess: (UnRegisterDeviceResponse?) -> Unit) {
        BsaSdk.getInstance().sdkService.unRegisterDevice(userKey, object :
            SdkResponseCallback<UnRegisterDeviceResponse> {
            override fun onSuccess(response: UnRegisterDeviceResponse?) {
                Log.d("BSA DEL", response?.rtCode.toString())
                onSucess(response)
            }

            override fun onFailed(errorResult: ErrorResult?) {
                Log.d("BSA DEL ERR", errorResult?.errorCode.toString())
            }
        })
    }

    fun deleteUser(onSucess: (DeleteUserResponse?) -> Unit) {
        BsaSdk.getInstance().sdkService.deleteUser(object :
            SdkResponseCallback<DeleteUserResponse> {
            override fun onSuccess(response: DeleteUserResponse?) {
                Log.d("BSA DEL", response?.rtCode.toString())
                onSucess(response)
            }

            override fun onFailed(errorResult: ErrorResult?) {
                Log.d("BSA DEL ERR", errorResult?.errorCode.toString())
            }

        })
    }

    fun deviceCheck(
        onSucess: (RegistrationData) -> Unit
    ) {
        BsaSdk.getInstance().sdkService.deviceCheck(object :
            SdkResponseCallback<MeResponse> {
            override fun onSuccess(response: MeResponse?) {
                Log.d("BSA", response?.data?.userKey.toString())
                val data = RegistrationData(
                    user = User(
                        userKey = response?.data?.userKey.toString(),
                        email = response?.data?.email.toString(),
                        phone = response?.data?.phoneNum.toString(),
                        name = response?.data?.name.toString(),
                    ),
                    authType = response?.data?.authType.toString(),
                    disposeToken = ""
                )
                onSucess(data)
            }

            override fun onFailed(errorResult: ErrorResult?) {
                Log.d("BSA DEL ERR", errorResult?.errorCode.toString())
            }

        })
    }

}