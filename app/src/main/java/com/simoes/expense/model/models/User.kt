package com.simoes.expense.model.models

import com.google.firebase.database.Exclude
import java.io.Serializable

class User : Serializable {

    lateinit var name    : String
    lateinit var email   : String
    @Exclude
    lateinit var senha   : String

}