package com.simoes.expense.model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.simoes.expense.helpers.FirebaseConfiguration
import java.lang.Exception
import java.util.ArrayList

class CRUDModel {

    companion object {

        fun findByUUID(`object`: Any, uuid: String) : Any? {
            return try {
                val firebase = FirebaseConfiguration.firebase
                val obj = `object`

                suspend {
                    val nameObject = getObjectName(`object`)

                    firebase.child(nameObject).orderByChild("uuid").equalTo(uuid).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            val obj = p0.children.elementAt(0).getValue(`object`::class.java)


                        }

                        override fun onCancelled(p0: DatabaseError) {

                        }
                    })

                }

                 obj
            }catch (e:Exception) {
                null
            }
        }

        fun findAll(`object`: Any): ArrayList<Any>?  {
             try {
                val firebase = FirebaseConfiguration.firebase
                val list = ArrayList<Any>()

               suspend {

                   val nameObject = getObjectName(`object`)

                   firebase.child(nameObject).addValueEventListener(object : ValueEventListener {
                       override fun onDataChange(p0: DataSnapshot) {

                           for (data in p0.children){
                               list.add(data)
                           }

                       }

                       override fun onCancelled(p0: DatabaseError) {

                       }
                   })
               }

                return list
            }catch (e:Exception) {
                 return null
            }
        }

        fun delete(`object`: Any, uuid: String) : Boolean {
            return try {
                val firebase = FirebaseConfiguration.firebase
                val nameObject = getObjectName(`object`)

                firebase.child(  nameObject ).child(uuid).removeValue()
                true

            }catch (e: Exception){
                false
            }
        }

        fun create(`object`: Any)  : Boolean {
            return try {
                val firebase = FirebaseConfiguration.firebase
                val nameObject = getObjectName(`object`)

                firebase.child( nameObject ).push().setValue(`object`)
                true

            }catch (e: Exception){
                false
            }
        }


        fun update(`object`: Any) : Boolean {
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