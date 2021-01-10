package com.simoes.expense.helpers

import com.simoes.expense.model.models.Expense
import java.text.SimpleDateFormat

class Helper {

    companion object {
        const val UUIDCARD = "uuidcard"
        const val EXPENSE_NAME = "expense_name"
        const val EXPENSE_CODE = 1

        fun expenseOwn(expense: Expense) : Boolean {
            val stringDueDate = "${expense.dueDate}/${DateHelper.nowMonth()}/${DateHelper.nowYear()}"
            val dueDate = SimpleDateFormat("dd/MM/yyyy").parse(stringDueDate).time
            val now = DateHelper.nowMilliseconds()
            return dueDate > now
        }

    }

}