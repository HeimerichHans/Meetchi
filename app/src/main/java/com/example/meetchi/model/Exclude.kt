package com.example.meetchi.model
import kotlin.reflect.full.memberProperties

/*
*******************************************************
*                 Model: Exclude                      *
*******************************************************
|  Description:                                       |
|  Ce model représente notre Base Exclude             |
|  Firebase.                                          |
*******************************************************
*/
data class Exclude(
    var last_insert: Int? = 0,
    var uid_1: String? = null,
    var uid_2: String? = null,
    var uid_3: String? = null,
    var uid_4: String? = null,
    var uid_5: String? = null,
    var uid_6: String? = null,
    var uid_7: String? = null,
    var uid_8: String? = null,
    var uid_9: String? = null,
    var uid_10: String? = null,
    var uid_11: String? = null,
    var uid_12: String? = null,
    var uid_13: String? = null,
    var uid_14: String? = null,
    var uid_15: String? = null,
    var uid_16: String? = null,
    var uid_17: String? = null,
    var uid_18: String? = null,
    var uid_19: String? = null,
    var uid_20: String? = null,
){
    fun contains_uid(chaine: String):Boolean
    {
        val properties = this::class.memberProperties

        for (property in properties) {
            val value = property.getter.call(this)
            if (value is String && value.contains(chaine)) {
                return true
            }
        }
        return false
    }

    fun insert_uid(uid: String){
        when(this.last_insert){
            0 -> {this.uid_1 = uid
                this.last_insert = 1}
            1 -> {this.uid_2 = uid
                this.last_insert = 2}
            2 -> {this.uid_3 = uid
                this.last_insert = 3}
            3 -> {this.uid_4 = uid
                this.last_insert = 4}
            4 -> {this.uid_5 = uid
                this.last_insert = 5}
            5 -> {this.uid_6 = uid
                this.last_insert = 6}
            6 -> {this.uid_7 = uid
                this.last_insert = 7}
            7 -> {this.uid_8 = uid
                this.last_insert = 8}
            8 -> {this.uid_9 = uid
                this.last_insert = 9}
            9 -> {this.uid_10 = uid
                this.last_insert = 10}
            10 -> {this.uid_11 = uid
                this.last_insert = 11}
            11 -> {this.uid_12 = uid
                this.last_insert = 12}
            12 -> {this.uid_13 = uid
                this.last_insert = 13}
            13 -> {this.uid_14 = uid
                this.last_insert = 14}
            14 -> {this.uid_15 = uid
                this.last_insert = 15}
            15 -> {this.uid_16 = uid
                this.last_insert = 16}
            16 -> {this.uid_17 = uid
                this.last_insert = 17}
            17 -> {this.uid_18 = uid
                this.last_insert = 18}
            18 -> {this.uid_19 = uid
                this.last_insert = 19}
            19 -> {this.uid_20 = uid
                this.last_insert = 0}
        }
    }
}
