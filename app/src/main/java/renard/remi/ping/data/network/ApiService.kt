package renard.remi.ping.data.network

import renard.remi.ping.data.network.dto.UserDto
import renard.remi.ping.data.network.dto.request.AuthRequest
import renard.remi.ping.data.network.dto.request.UpdateFcmTokenRequest
import renard.remi.ping.data.network.dto.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body authRequest: AuthRequest): AuthResponse

    @POST("create-account")
    suspend fun createAccount(@Body authRequest: AuthRequest): AuthResponse

    @GET("me")
    suspend fun getMe(@Header("Authorization") accessToken: String): UserDto?

    @POST("update-fcm-token")
    suspend fun updateFcmToken(
        @Header("Authorization") accessToken: String,
        @Body updateFcmTokenRequest: UpdateFcmTokenRequest
    )
}