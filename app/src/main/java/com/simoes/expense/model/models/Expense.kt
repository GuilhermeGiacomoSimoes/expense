package com.simoes.expense.model.models

class Expense : Object() {
    lateinit var name    : String
    lateinit var card    : Card

    var paidOut : Boolean = false
    var isFixed : Boolean = false
    var value   : Double  = .0
    var repeat  : Boolean = false
    var dueDate : Int     = 0
}