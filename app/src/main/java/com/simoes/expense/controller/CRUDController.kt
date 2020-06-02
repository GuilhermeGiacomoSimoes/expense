package com.simoes.expense.controller

import androidx.fragment.app.FragmentManager
import com.simoes.expense.model.CRUDModel
import com.simoes.expense.view.fragments.FeedbackDialog
import java.lang.Exception

class CRUDController {


    companion object {

        fun findByUUID(`object`: Any, uuid: String, fragmentManager: FragmentManager) : Any?{
            try {

                val obj = CRUDModel.findByUUID(`object`, uuid)

                if (obj != null) {
                    return obj
                }
                else {
                    FeedbackDialog.showDialog( fragmentManager , "Erro ao deletar" )
                }


            }catch (e:Exception) {
                FeedbackDialog.showDialog( fragmentManager, e.toString() )
            }

            return null
        }

        fun findAll(`object`: Any, fragmentManager: FragmentManager) : ArrayList<Any> ? {
            try {

                val list = CRUDModel.findAll(`object`)

                if(list != null){
                    FeedbackDialog.showDialog( fragmentManager, "Deletado com sucesso" )
                    return list
                }
                else {
                    FeedbackDialog.showDialog( fragmentManager, "Erro ao deletar" )
                }
            }catch (e:Exception) {
                FeedbackDialog.showDialog( fragmentManager, e.toString() )
            }

            return null
        }

        fun delete (`object`: Any, uuid: String, fragmentManager: FragmentManager) {
            try {
                if(CRUDModel.delete(`object`, uuid)){
                    FeedbackDialog.showDialog( fragmentManager, "Deletado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( fragmentManager, "Erro ao deletar" )
                }

            }catch (e: Exception){
                FeedbackDialog.showDialog( fragmentManager, e.toString() )
            }
        }

        fun createOrUpdate(`object`: Any, fragmentManager: FragmentManager)  {
            try {
                if(CRUDModel.createOrUpdate(`object`)) {
                    FeedbackDialog.showDialog( fragmentManager, "Cadastrado com sucesso" )
                }
                else {
                    FeedbackDialog.showDialog( fragmentManager, "Erro ao cadastrar" )
                }

            }catch (e: Exception){
                FeedbackDialog.showDialog( fragmentManager, e.toString() )
            }
        }
    }


}