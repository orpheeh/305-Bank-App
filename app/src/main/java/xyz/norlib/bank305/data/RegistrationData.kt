package xyz.norlib.bank305.data

data class RegistrationData(
    var user: User,
    var disposeToken: String?,
    var authType: String?,
    var isLogin: Boolean = false
)