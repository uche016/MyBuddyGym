package com.example.mybuddygym.utils

data class AppUser(
    private val userId: String,
    private val firstName: String,
    private val lastName: String,
    private val accessLevel: String,
    private val password: String,
    private val hubId: String,
    private val appVersionNo: String,

    ) {
    fun getUserId(): String = userId


    fun getFirstName(): String = firstName

    fun getLastName(): String = lastName

    fun getAccessLevel(): String = accessLevel

    fun getPassword(): String = password

    fun getHubId(): String = hubId

    fun getAppVersionNo(): String = appVersionNo

    override fun toString(): String {
        return "AppUser(\n" +
                "userId='$userId'\n" +
                "firstName='$firstName'\n" +
                "lastName='$lastName'\n" +
                "accessLevel='$accessLevel'\n" +
                "password='$password'\n" +
                "hubId='$hubId'\n" +
                "appVersionNo='$appVersionNo'\n" +
                ")"
    }
}
