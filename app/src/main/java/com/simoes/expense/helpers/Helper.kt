package com.simoes.expense.helpers

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.simoes.expense.R
import com.simoes.expense.model.models.Expense
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Helper {

    companion object {
        const val UUIDCARD              = "uuidcard"
        const val EXPENSE_NAME          = "expense_name"
        const val EXPENSE_CODE          = 1
        const val PERSIST_VIEW_BALANCE  = "PERSIST_VIEW_BALANCE"
        const val DELETE_EXPENSE        = 2
        const val PAYMENT_EXPENSE       = 3
        const val EXPENSE_RETURN        = "EXPENSE_RETURN"

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

        fun persistData(REF_NAME: String, context: Context, args: String) {
            val editor = configSharedPreference(REF_NAME, context).edit()
            editor.putString(REF_NAME, args)
            editor.apply()
        }

        fun getPersistData(REF_NAME: String, context: Context) : String? {
            val editor = configSharedPreference(REF_NAME, context)
            return editor.getString(REF_NAME, false.toString())
        }

        private fun configSharedPreference(REF_NAME: String, context: Context) = context.getSharedPreferences(
            REF_NAME,
            Context.MODE_PRIVATE
        )

        fun getTranslateExpenseTypePortuguese(key: String) : String? {
            val hash = hashMapOf(
                "PUB" to "Bar",
                "SUPERMARKET" to "Supermercado",
                "ALCOHOLIC_BEVERAGES" to "Bebidas Alcóolicas",
                "TICKETS" to "Passagens",
                "FOOD" to "Comida",
                "INTERNET" to "Internet",
                "WATER" to "Água",
                "ENERGY" to "Energia",
                "FURNITURE" to "Mobília",
                "OTHER" to "Outros"
            )

            return hash[key]
        }

        fun getIconTypeCategory(key: String) : Int? {
            val hash = hashMapOf(
                "PUB" to R.drawable.ic_pub,
                "SUPERMARKET" to R.drawable.ic_supermarket,
                "ALCOHOLIC_BEVERAGES" to R.drawable.ic_alcoholic_beverages,
                "TICKETS" to R.drawable.ic_tickets,
                "FOOD" to R.drawable.ic_food,
                "INTERNET" to R.drawable.ic_internet,
                "WATER" to R.drawable.ic_water,
                "ENERGY" to R.drawable.ic_energy,
                "FURNITURE" to R.drawable.ic_furniture,
                "OTHER" to R.drawable.ic_other
            )

            return hash[key]
        }

        fun getColorByTypeCategory( key : String ) : Int? {
            val hash = hashMapOf(
                "PUB" to Color.rgb(210,105,30),
                "SUPERMARKET" to Color.rgb(70,130,180),
                "ALCOHOLIC_BEVERAGES" to Color.rgb(255,215,0),
                "TICKETS" to Color.rgb(50,205,50),
                "FOOD" to Color.rgb(188,143,143),
                "INTERNET" to Color.rgb(238,130,238),
                "WATER" to Color.rgb(123,104,238),
                "ENERGY" to Color.rgb(199,21,133),
                "FURNITURE" to Color.rgb(250,128,114),
                "OTHER" to Color.rgb(255,0,0)
            )

            return hash[key]
        }

    }

}