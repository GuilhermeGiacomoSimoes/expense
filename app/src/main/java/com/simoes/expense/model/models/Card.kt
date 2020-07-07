package com.simoes.expense.model.models

import com.simoes.expense.helpers.FlagCards

class Card  : Object() {

    lateinit    var flagCards: FlagCards
    lateinit    var name     : String
                var balance  = .0
                var dueDate  =  0
                var spent    = .0
}