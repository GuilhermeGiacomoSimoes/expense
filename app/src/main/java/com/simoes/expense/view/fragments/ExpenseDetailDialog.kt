package com.simoes.expense.view.fragments

import android.content.Intent
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
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.expense_detail_dialog.*
import kotlinx.android.synthetic.main.fragment_expense.*
import java.util.ArrayList

class ExpenseDetailDialog : DialogFragment() {

    private lateinit var expense        : Expense
    private lateinit var listView       : ListView

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
                CRUDController.update( expense, fragmentManager!!, context!! )

                val intent = Intent().putExtra( Helper.EXPENSE_NAME, expense )
                targetFragment?.onActivityResult(targetRequestCode, Helper.EXPENSE_CODE, intent)
                dismiss()
                this.listView.deferNotifyDataSetChanged()
            }
        }

        btn_delete_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                CRUDController.delete( expense,  fragmentManager!!, context!! )
                dismiss()
                this.listView.deferNotifyDataSetChanged()
            }
        }
    }

    companion object {
        var instance = ExpenseDetailDialog()

        fun showDialog( fragmentManager: FragmentManager, expense: Expense, listView: ListView, listExpense : ArrayList ) {
            with(instance) {
                if (!isAdded) {
                    this.expense    = expense
                    this.listView   = listView
                    show(fragmentManager, "")
                }
            }
        }

    }

}