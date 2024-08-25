package xyz.norlib.bank305.business.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xyz.norlib.bank305.business.model.TransactionModel
import xyz.norlib.bank305.business.model.UserModel
import xyz.norlib.bank305.data.Transaction

private const val BASE_URL =
    "https://spl-remote.b305.norlib.xyz"
//"https://android-kotlin-fun-mars-server.appspot.com/photos"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("user/{user}")
    suspend fun getUser(@Path("user") user: String): UserModel

    @GET("user/transactions/{user}")
    suspend fun getTransactions(@Path("user") user: String): List<TransactionModel>

    @POST("user/transactions")
    suspend fun createTransaction(@Body transaction: TransactionModel): TransactionModel

    @POST("user")
    suspend fun createUser(@Body user: UserModel): UserModel
}

object BankApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}