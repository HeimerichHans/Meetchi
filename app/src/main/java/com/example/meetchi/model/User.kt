package com.example.meetchi.model

import java.util.Date

/*
*******************************************************
*                   Model: User                       *
*******************************************************
|  Description:                                       |
|  Ce model représente notre Base Utilisateur         |
|  Firebase.                                          |
*******************************************************
*/
data class User(
    var uid: String? = null,
    var account_ready: Boolean? = false,
    var dateCreation: Date? = null,
    var dateNaissance: Date? = null,
    var dateUpdate: Date? = null,
    var description: String? = null,
    var nom: String? = null,
    var prenom: String? = null,
    var pseudonyme: String? = null,
    var genre: String? = null,
    var token: String? = null
)
