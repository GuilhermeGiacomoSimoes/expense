package com.simoes.expense.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.expense_detail_dialog.*

class ExpenseDetailDialog : DialogFragment() {

    private lateinit var expense       : Expense

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            CRUDController.update( expense, fragmentManager!!, context!! )
        }

        btn_delete_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                CRUDController.delete( expense,  fragmentManager!!, context!! )
            }
        }
    }

    companion object {
        var instance = ExpenseDetailDialog()

        fun showDialog( fragmentManager: FragmentManager, context: Context, expense: Expense ) {
            with(instance) {
                if (!isAdded) {
                    isCancelable = false
                    this.expense = expense
                    show(fragmentManager, "")
                }
            }
        }

    }

}