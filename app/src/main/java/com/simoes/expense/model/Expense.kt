package com.simoes.expense.model

import java.io.Serializable

class Expense : Serializable {

    lateinit var uuid    : String
    lateinit var name    : String
    lateinit var dueDate : String
    lateinit var bank    : Bank
    lateinit var paidBy  : User

    var paidOut: Boolean = false
    var isFixed: Boolean = false
    var value: Double    = .0

}