package br.com.connectattoo.data

data class AddressData (
    val street: String,
    val number: String,
    val city: String,
    val state: String,
    val country: String = "Brasil",
    val zipCode: String
)
