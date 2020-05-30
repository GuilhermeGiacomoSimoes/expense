package com.simoes.expense.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R

class ConfirmAndDetailsDialog : DialogFragment() {

    private lateinit var title          : String
    private lateinit var description    : String
    private lateinit var titleBtnCancel : String
    private lateinit var titleBtnOk     : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_and_details_dialog, container, false)
    }

    companion object {
        private fun getInstance()  = ConfirmAndDetailsDialog()

        fun showDialog(fragmentManager: FragmentManager, title: String, description: String?, titleBtnOk : String?, titleBtnCancel: String?) {
            with(getInstance()) {
                if(!isAdded) {
                    isCancelable = false

                    this.title = title
                    if( !description.isNullOrEmpty() ) {
                        this.description = description
                    }

                    if(!titleBtnOk.isNullOrEmpty()) {
                        this.titleBtnOk = titleBtnOk
                    }

                    if(!titleBtnCancel.isNullOrEmpty()) {
                        this.titleBtnCancel = titleBtnCancel
                    }

                    show(fragmentManager, "")
                }
            }
        }

        fun dismissLoading() {
            with(getInstance()) {
                if(isAdded) {
                    dismiss()
                }
            }
        }
    }
}