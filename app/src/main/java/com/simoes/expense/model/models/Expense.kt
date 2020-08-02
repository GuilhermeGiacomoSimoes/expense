package com.simoes.expense.model.models

import java.io.Serializable

class Expense : Object(), Serializable {

             lateinit   var name    : String
    override lateinit   var uuid     : String

    var paidOut : Boolean = false
    var isFixed : Boolean = false
    var value   : Double  = .0
    var repeat  : Boolean = false
    var dueDate : Int     = 0
    var card    : Card?   = null
}