package xyz.norlib.bank305.data

val fakeUser = User(
    userKey = "orphee",
    name = "Orphée NVE",
    email = "orphee.nve@aninf.ga",
    phone = "+241077598300"
)

data class User(val userKey: String, val name: String, val email: String, val phone: String)