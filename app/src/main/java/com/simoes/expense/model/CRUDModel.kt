package com.simoes.expense.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.FirebaseConfiguration
import com.simoes.expense.model.models.Object
import java.lang.Exception
import java.util.*

class CRUDModel {

    companion object {

        fun findByUUID(`object`: Object, uuid: String) : Object? {
            return try {
                val firebase    = FirebaseConfiguration.firebase
                val nameObject  = getObjectName(`object`)

                firebase.child(nameObject).orderByChild("uuid").equalTo(uuid).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {

                        val obj = p0.children.elementAt(0).getValue(`object`::class.java)


                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }
                })



                 obj
            }catch (e:Exception) {
                null
            }
        }

        fun findAll(`object`: Object, callBack : CallBackReturn ) : Boolean {
             try {

                val firebase        = FirebaseConfiguration.firebase
                val list            = ArrayList<Any>()
                val nameObject      = getObjectName(`object`)

                 firebase.child(nameObject).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {

                       for (data in p0.children){

                           if ( data.getValue(`object` ::class.java) != null) {

                               val objectClass  = data.getValue(`object` ::class.java)  !!
                               objectClass.uuid = data.key                              !!

                               list.add( objectClass )
                           }
                       }

                        if ( list.isNotEmpty() ) {
                            callBack.callback( list )
                        }

                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("erro", "deu ruim")
                    }
                })


                return true

            }catch (e:Exception) {
                 return false
            }
        }

        fun delete(`object`: Object, uuid: String) : Boolean {
            return try {
                val firebase = FirebaseConfiguration.firebase
                val nameObject = getObjectName(`object`)

                firebase.child(  nameObject ).child(uuid).removeValue()
                true

            }catch (e: Exception){
                false
            }
        }

        fun create(`object`: Object)  : Boolean {
            return try {
                val firebase   = FirebaseConfiguration.firebase
                val nameObject = getObjectName(`object`)

                `object`.uuid = ""

                firebase.child( nameObject ).push().setValue(`object`)
                true

            }catch (e: Exception){
                false
            }
        }


        fun update(`object`: Object) : Boolean {
            return try {
                val firebase = FirebaseConfiguration.firebase
                val nameObject = getObjectName(`object`)

                firebase.child( nameObject ).setValue(`object`)
                true

            }catch (e: Exception){
                false
            }
        }


        private fun getObjectName(`object` : Any) : String {
            val arrayPathObject = `object`.javaClass.name.split(".")
            return arrayPathObject[ arrayPathObject.size - 1 ]
        }

    }

}