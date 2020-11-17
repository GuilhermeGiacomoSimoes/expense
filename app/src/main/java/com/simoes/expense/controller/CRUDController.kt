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

        fun findByUUID(`object`: com.simoes.expense.model.models.Object, uuid: String, fragmentManager: FragmentManager, context: Context) : Any?{

            if ( verifyConnection( context, fragmentManager ) ) {
                try {

                    val obj = CRUDModel.findByUUID(`object`, uuid)

                    if (obj != null) {
                        return obj
                    }
                    else {
                        //FeedbackDialog.showDialog( fragmentManager , "Erro ao deletar", "Erro")
                    }

                }catch (e:Exception) {
                    //FeedbackDialog.showDialog( fragmentManager, e.toString(), "Erro" )
                }
            }

            return null
        }

        fun findAll(`object`: com.simoes.expense.model.models.Object, fragmentManager: FragmentManager, callBack: CallBackReturn, context: Context) : ArrayList<Any>  {
            if ( verifyConnection( context, fragmentManager ) ) {
                CRUDModel.findAll(`object`, callBack)
            }
            return ArrayList()
        }

        fun delete (`object`: com.simoes.expense.model.models.Object, fragmentManager: FragmentManager, context: Context) {
            if ( verifyConnection( context, fragmentManager ) ) {
                CRUDModel.delete(`object`, `object`.uuid)
            }
        }

        fun create(`object`: com.simoes.expense.model.models.Object, fragmentManager: FragmentManager, context: Context)  {
            if ( verifyConnection( context, fragmentManager ) ) {
                CRUDModel.create(`object`)
            }
        }

        fun update(`object`: com.simoes.expense.model.models.Object, fragmentManager: FragmentManager, context: Context) {
            if ( verifyConnection( context, fragmentManager ) ) {
                CRUDModel.update(`object`)
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