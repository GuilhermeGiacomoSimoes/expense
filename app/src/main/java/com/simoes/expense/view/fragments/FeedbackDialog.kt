package com.simoes.expense.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import kotlinx.android.synthetic.main.error_dialog.*

class FeedbackDialog: DialogFragment() {

    private lateinit var description    : String
    private lateinit var title          : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.error_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        description_dialog_error    .text = description
        title_dialog_error          .text = title

        btn_positive_error.setOnClickListener {
            super.dismiss()
        }

        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT) )
    }

    companion object {
        private fun getInstance() = FeedbackDialog()

        fun showDialog(fragmentManager: FragmentManager, description: String?, title: String?) {
            with(getInstance()) {
                if(!isAdded) {
                    isCancelable = false

                    if( !description.isNullOrEmpty() ) {
                        this.description = description
                    }

                    if ( !title.isNullOrEmpty() ) {
                        this.title = title
                    }

                    show(fragmentManager, "")
                }
            }
        }

    }
}