package com.simoes.expense.helpers

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseConfiguration {

    companion object {
        val firebase = FirebaseDatabase.getInstance().reference


        fun teste ( collection : String) : DatabaseReference {
            return FirebaseDatabase.getInstance().getReference( collection )
        }
    }

}