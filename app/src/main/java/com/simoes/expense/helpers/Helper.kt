package com.simoes.expense.helpers

import android.content.Context
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
        const val PERSIST_VIEW_BALANCE = "PERSIST_VIEW_BALANCE"
        const val DELETE_EXPENSE = 2
        const val PAYMENT_EXPENSE = 3
        const val EXPENSE_RETURN = "EXPENSE_RETURN"

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

        fun persistData (REF_NAME : String, context: Context, args : String) {
            val editor = configSharedPreference(REF_NAME, context).edit()
            editor.putString(REF_NAME, args)
            editor.apply()
        }

        fun getPersistData (REF_NAME : String, context: Context) : String? {
            val editor = configSharedPreference(REF_NAME, context)
            return editor.getString(REF_NAME, false.toString())
        }

        private fun configSharedPreference(REF_NAME : String, context: Context) = context.getSharedPreferences(REF_NAME, Context.MODE_PRIVATE)

    }

}