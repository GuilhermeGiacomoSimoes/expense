package com.simoes.expense.controller

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.ConnectionHelper
import com.simoes.expense.model.CRUDModel
import com.simoes.expense.view.fragments.FeedbackDialog
import java.lang.Exception
import java.util.ArrayList

class CRUDController {

    companion object {

        fun findByUUID(`object`: Any, uuid: String, fragmentManager: FragmentManager, context: Context) : Any?{

            if ( verifyConnection( context, fragmentManager ) ) {
                try {

                    val obj = CRUDModel.findByUUID(`object`, uuid)

                    if (obj != null) {
                        return obj
                    }
                    else {
                        FeedbackDialog.showDialog( fragmentManager , "Erro ao deletar", "Erro")
                    }

                }catch (e:Exception) {
                    FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }
            }

            return null
        }

        fun findAll(`object`: Any, fragmentManager: FragmentManager, callBack: CallBackReturn, context: Context) : ArrayList<Any>  {

            if ( verifyConnection( context, fragmentManager ) ) {
                try {

                    if ( ! CRUDModel.findAll(`object`, callBack) ) {
                        FeedbackDialog.showDialog( fragmentManager, "Erro ao buscar dados", "Erro" )
                    }

                }catch (e:Exception) {
                    FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }
            }

            return ArrayList()
        }

        fun delete (`object`: Any, uuid: String, fragmentManager: FragmentManager, context: Context) {

            if ( verifyConnection( context, fragmentManager ) ) {

                try {
                    if(CRUDModel.delete(`object`, uuid)){
                        FeedbackDialog.showDialog( fragmentManager, "Deletado com sucesso", "OK" )
                    }
                    else {
                        FeedbackDialog.showDialog( fragmentManager, "Erro ao deletar", "Erro" )
                    }

                }catch (e: Exception){
                    FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }

            }


        }

        fun create(`object`: Any, fragmentManager: FragmentManager, context: Context)  {

            if ( verifyConnection( context, fragmentManager ) ) {
                try {
                    if(CRUDModel.create(`object`)) {
                        FeedbackDialog.showDialog( fragmentManager, "Cadastrado com sucesso", "OK" )
                    }
                    else {
                        FeedbackDialog.showDialog( fragmentManager, "Erro ao cadastrar", "Erro" )
                    }

                }catch (e: Exception){
                    FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }
            }


        }

        fun update(`object`: Any, fragmentManager: FragmentManager, context: Context) {
            if ( verifyConnection( context, fragmentManager ) ) {
                try {
                    if(CRUDModel.update(`object`)) {
                        FeedbackDialog.showDialog( fragmentManager, "Editado com sucesso", "OK" )
                    }
                    else {
                        FeedbackDialog.showDialog( fragmentManager, "Erro ao editar", "Erro" )
                    }

                }catch (e: Exception){
                    FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }
            }


        }

        private fun verifyConnection( context: Context, fragmentManager: FragmentManager ) : Boolean {

            if ( !ConnectionHelper.isConnected( context ) ) {
                FeedbackDialog.showDialog( fragmentManager, "conecte-se a uma rede", "Sem conexão com a internet" )
                return false
            }

            return true
        }

    }


}