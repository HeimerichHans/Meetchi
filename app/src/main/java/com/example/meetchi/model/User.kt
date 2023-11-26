package com.example.meetchi.model

import java.util.Date

data class User(
    var account_ready: Boolean? = false,
    var dateCreation: Date? = null,
    var dateNaissance: Date? = null,
    var dateUpdate: Date? = null,
    var description: String? = null,
    var nom: String? = null,
    var prenom: String? = null,
    var pseudonyme: String? = null,
    var genre: String? = null,
)