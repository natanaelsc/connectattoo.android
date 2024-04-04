package br.com.connectattoo.data

import com.google.gson.annotations.SerializedName

data class ApiConfirmationResponse(
    @SerializedName("emailConfirmed")
    val emailConfirmed: Boolean
)
