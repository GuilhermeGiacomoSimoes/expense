package com.simoes.expense.model

import java.io.Serializable

class Bank  : Serializable {
    lateinit var uuid    : String
    lateinit var name    : String

    var balance : Double = .0
}