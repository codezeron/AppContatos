package com.example.appcontatos.data

data class Contact(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isFavorite: Boolean = false
){
    val fullName get(): String = "$firstName $lastName".trim()
}
//classe imutavel
fun List<Contact>.groupByInitial(): Map<String, List<Contact>> =
    sortedBy { it.fullName }
        .groupBy { it.fullName.take(1) }