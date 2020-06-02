package com.simoes.expense.controller

import android.app.Activity
import com.simoes.expense.model.CRUDModel
import com.simoes.expense.view.fragments.FeedbackDialog
import java.lang.Exception

class CRUDController {


    companion object {

        fun findByUUID(`object`: Any, uuid: String, context: Activity) : Any?{
            try {

                val obj = CRUDModel.findByUUID(`object`, uuid)

                if (obj != null) {
                    return obj
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao deletar" )
                }


            }catch (e:Exception) {
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }

            return null
        }

        fun findAll(`object`: Any, context: Activity) : ArrayList<Any> ? {
            try {

                val list = CRUDModel.findAll(`object`)

                if(list != null){
                    FeedbackDialog.showDialog( context.fragmentManager, "Deletado com sucesso" )
                    return list
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao deletar" )
                }
            }catch (e:Exception) {
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }

            return null
        }

        fun delete (`object`: Any, uuid: String, context: Activity) {
            try {
                if(CRUDModel.delete(`object`, uuid)){
                    FeedbackDialog.showDialog( context.fragmentManager, "Deletado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao deletar" )
                }

            }catch (e: Exception){
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }
        }

        fun createOrUpdate(`object`: Any, context: Activity)  {
            try {
                if(CRUDModel.createOrUpdate(`object`)) {
                    FeedbackDialog.showDialog( context.fragmentManager, "Cadastrado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao cadastrar" )
                }

            }catch (e: Exception){
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }
        }
    }


}