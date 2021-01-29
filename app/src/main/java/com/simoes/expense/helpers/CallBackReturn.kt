package com.simoes.expense.helpers

import java.util.ArrayList

interface CallBackReturn {
    fun callback( list : ArrayList<Any> )
    fun callback( isSuccess : Boolean )
}