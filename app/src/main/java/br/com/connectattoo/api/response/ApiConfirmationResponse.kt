package br.com.connectattoo.api.response

import com.google.gson.annotations.SerializedName

data class ApiConfirmationResponse(
    @SerializedName("emailConfirmed")
    val emailConfirmed: Boolean
)
