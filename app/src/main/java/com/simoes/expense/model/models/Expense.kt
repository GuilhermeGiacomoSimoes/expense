package com.simoes.expense.model.models

import com.simoes.expense.helpers.TypeCategory
import com.simoes.expense.helpers.TypeExpense
import java.io.Serializable

class Expense : Object(), Serializable {

             lateinit var typeExpense   : TypeExpense
             lateinit   var name        : String
             lateinit   var date        : String
             lateinit   var category    : TypeCategory
            override lateinit   var uuid        : String

    var paidOut : Boolean = false
    var value   : Double  = .0
    var repeat  : Boolean = false
    var dueDate : String  = ""
    var card    : Card?   = null
    var datePaid = ArrayList<String>()

}