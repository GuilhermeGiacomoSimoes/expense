package com.simoes.expense.helpers

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.simoes.expense.model.models.Expense
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateNow () :  String {
            val dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss")
            val now = LocalDateTime.now()
            return dtf.format(now)
        }


        private fun configSharedPreference(REF_NAME : String, context: Context) = context.getSharedPreferences(REF_NAME, Context.MODE_PRIVATE)

    }

}