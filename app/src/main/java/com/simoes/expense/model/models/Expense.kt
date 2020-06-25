package com.simoes.expense.model.models

class Expense : Object() {

    lateinit var name    : String
    lateinit var dueDate : String
    lateinit var bank    : Bank
    lateinit var paidBy  : User

    var paidOut: Boolean = false
    var isFixed: Boolean = false
    var value: Double    = .0

}