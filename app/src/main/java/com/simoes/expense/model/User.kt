package com.simoes.expense.model

import com.google.firebase.database.Exclude
import java.io.Serializable

class User : Serializable {

    lateinit var uuid    : String
    lateinit var name    : String
    lateinit var email   : String
    @Exclude
    lateinit var senha   : String
    
}