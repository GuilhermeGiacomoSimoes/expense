package com.simoes.expense.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import kotlinx.android.synthetic.main.error_dialog.*

class ErrorDialog: DialogFragment() {

    private lateinit var description    : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_and_details_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        description_dialog_error.text = description
        btn_positive_error.setOnClickListener {
            dismissLoading()
        }
    }

    companion object {
        private fun getInstance()  = ErrorDialog()

        fun showDialog(fragmentManager: FragmentManager, description: String?) {
            with(getInstance()) {
                if(!isAdded) {
                    isCancelable = false

                    if( !description.isNullOrEmpty() ) {
                        this.description = description
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