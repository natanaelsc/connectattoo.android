package br.com.connectattoo.domain

data class ArtistDomain (
    val name: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val termsAccepted: Boolean,
    val address: ArtistAddress
)

data class ArtistAddress (
    val zipCode: String,
    val street: String,
    val number: String,
    val city: String,
    val state: String
)
