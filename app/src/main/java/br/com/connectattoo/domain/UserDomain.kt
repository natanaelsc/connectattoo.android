package br.com.connectattoo.domain

data class UserDomain (
    val name: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val termsAccepted: Boolean
)
