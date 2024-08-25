package xyz.norlib.bank305.business.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import xyz.norlib.bank305.business.model.UserModel

private const val BASE_URL =
    "https://spl-remote.b305.norlib.xyz/user/orpheenve@hotmail.com"
//"https://android-kotlin-fun-mars-server.appspot.com/photos"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("")
    suspend fun getUser(): List<UserModel>
}

object BankApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}