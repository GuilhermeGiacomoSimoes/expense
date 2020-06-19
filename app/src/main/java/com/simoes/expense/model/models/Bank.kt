package com.simoes.expense.model.models

import com.google.firebase.database.Exclude
import java.io.Serializable

class Bank  : Serializable {
    @Exclude
    lateinit var uuid    : String
    lateinit var name    : String

    var balance : Double = .0
}