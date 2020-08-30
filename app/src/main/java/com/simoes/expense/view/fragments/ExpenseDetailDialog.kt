package com.simoes.expense.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.expense_detail_dialog.*

class ExpenseDetailDialog : DialogFragment() {

    private lateinit var expense : Expense

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT) )

        configureTexts()
        configureButtons()
    }

    private fun configureTexts() {
        title_expense_dialog.text = expense.name
        value_expense_dialog.text = expense.value.toString()
        date_expense_dialog.text  = expense.dueDate.toString()
    }

    private fun configureButtons() {
        btn_payment_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                expense.paidOut = true
                CRUDController.update( expense, fragmentManager!!, context!! )
                dismiss()
            }
        }

        btn_delete_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                CRUDController.delete( expense,  fragmentManager!!, context!! )
                dismiss()
            }
        }
    }

    companion object {
        var instance = ExpenseDetailDialog()

        fun showDialog( fragmentManager: FragmentManager, expense: Expense, listView: ListView) {
            with(instance) {
                if (!isAdded) {
                    this.expense    = expense
                    show(fragmentManager, "")
                }
            }
        }

    }

}