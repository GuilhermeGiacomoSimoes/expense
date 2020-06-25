package com.simoes.expense.model.models

import com.google.firebase.database.Exclude

class User : Object() {

    lateinit var name    : String
    lateinit var email   : String
    @Exclude
    lateinit var senha   : String

}