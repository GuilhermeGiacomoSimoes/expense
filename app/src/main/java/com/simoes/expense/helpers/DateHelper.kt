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

        fun nowMilliseconds() : Long  {
            return Date( getDateNow() ).time
        }

        fun nowMonth() : String {
            val month = Date( getDateNow() ).month + 1
            return month.toString()
        }

        fun nowYear() : String {
            val year = Date(getDateNow()).year
            return year.toString()
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateNow() : String {
            return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( Date() )
        }
    }

}