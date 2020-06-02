package com.simoes.expense.controller

import com.simoes.expense.model.CRUDModel
import java.lang.Exception

class CRUDController {

    companion object {

        fun create(`object`: Any)  {
            try {
                CRUDModel.create(`object`)
            }catch (e: Exception){

            }
        }
    }


}