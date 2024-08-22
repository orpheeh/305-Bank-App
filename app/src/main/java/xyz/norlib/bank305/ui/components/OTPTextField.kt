package xyz.norlib.bank305.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OTPTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    numDigits: Int = 6,
    isMasked: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 0 until numDigits) {
            OutlinedTextField(
                value = if (otp.length > i) otp[i].toString() else "",
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        val newOtp = otp.substring(0, i) + newValue + otp.substring(i + 1)
                        onOtpChange(newOtp)
                    }
                },
                modifier = Modifier
                    .width(40.dp)
                    .padding(4.dp),
                singleLine = true,
                visualTransformation = if (isMasked) PasswordVisualTransformation() else VisualTransformation.None
            )
        }
    }
}