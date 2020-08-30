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

        //ex 30/08/2020
        fun nowOnlyDate() : String {
            return getDateNow().split(" ")[0]
        }

        //ex 06:26:00
        fun nowOnlyHours() : String  {
            return getDateNow().split(" ")[1]
        }

        fun nowMilliseconds() : String  {
            return Date( getDateNow() ).time.toString()
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateNow() : String {
            return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( Date() )
        }
    }

}