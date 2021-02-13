package com.simoes.expense.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    companion object {
        //ex 30/08/2020 06:26:00
        fun nowExtensive() : String {
            return getDateNow()
        }

        fun nowMilliseconds() : Long  {
            return System.currentTimeMillis()
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateNow() : String {
            return SimpleDateFormat("dd/MM/yyyy HH:mm").format( Date() )
        }
    }

}