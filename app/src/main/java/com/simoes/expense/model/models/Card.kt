package com.simoes.expense.model.models

import com.simoes.expense.helpers.FlagCards
import java.io.Serializable
import java.util.ArrayList

class Card  : Serializable, Object() {

    lateinit            var flagCards: FlagCards
    lateinit            var name     : String
                        var balance  = .0
                        var dueDate  =  0
                        var limit    = .0
                        var expenses = ArrayList<Expense>()
    override lateinit   var uuid      : String

}