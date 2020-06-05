package com.simoes.expense.helpers

import com.google.firebase.database.FirebaseDatabase

class FirebaseConfiguration {

    companion object {
        val firebase = FirebaseDatabase.getInstance().reference
    }

}