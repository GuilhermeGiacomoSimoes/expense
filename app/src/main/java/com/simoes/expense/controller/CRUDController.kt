package com.simoes.expense.controller

import android.app.Activity
import com.simoes.expense.model.CRUDModel
import com.simoes.expense.view.fragments.FeedbackDialog
import java.lang.Exception

class CRUDController {


    companion object {

        fun findByUUID(`object`: Any, uuid: String, context: Activity){
            try {
                if(CRUDModel.findByUUID(`object`, uuid)){
                    FeedbackDialog.showDialog( context.fragmentManager, "Deletado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao deletar" )
                }
            }catch (e:Exception) {
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }
        }

        fun findAll(`object`: Any, context: Activity){
            try {
                if(CRUDModel.findAll(`object`)){
                    FeedbackDialog.showDialog( context.fragmentManager, "Deletado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( context.fragmentManager, "Erro ao deletar" )
                }
            }catch (e:Exception) {
                FeedbackDialog.showDialog( context.fragmentManager, e.toString() )
            }
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