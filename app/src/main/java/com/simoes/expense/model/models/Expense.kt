package com.simoes.expense.model.models

import com.simoes.expense.helpers.TypeExpense
import java.io.Serializable

class Expense : Object(), Serializable {

             lateinit var typeExpense   : TypeExpense
             lateinit   var name        : String
    override lateinit   var uuid        : String

    var paidOut : Boolean = false
    var value   : Double  = .0
    var repeat  : Boolean = false
    var dueDate : Int     = 0
    var card    : Card?   = null
}