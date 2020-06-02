package com.simoes.expense.model

import com.simoes.expense.helpers.FirebaseConfiguration
import java.lang.Exception

class CRUDModel {

    companion object {

        fun create(`object`: Any)  : Boolean {
            return try {

                val firebase = FirebaseConfiguration.firebase

                firebase.child( `object`.javaClass.name ).setValue(`object`)

                true

            }catch (e: Exception){

                false
            }
        }

    }

}