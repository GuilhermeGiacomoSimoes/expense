package com.simoes.expense.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.expense_detail_dialog.*

class ExpenseDetailDialog : DialogFragment() {

    private lateinit var expense  : Expense
    private var position = 0

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

                val intent = Intent()
                intent.putExtra(Helper.EXPENSE_NAME, this.position)

                targetFragment?.onActivityResult(Helper.PAYMENT_EXPENSE, Activity.RESULT_OK, intent)
                dismiss()
            }
        }

        btn_delete_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                CRUDController.delete( expense,  fragmentManager!!, context!!)

                val intent = Intent()
                intent.putExtra(Helper.EXPENSE_NAME, this.position)

                targetFragment?.onActivityResult(Helper.EXPENSE_CODE, Activity.RESULT_OK, intent)
                dismiss()
            }
        }
    }

    companion object {
        var instance = ExpenseDetailDialog()

        fun showDialog( fragmentManager: FragmentManager, expense: Expense , position : Int, fragment : Fragment) {
            with(instance) {
                if (!isAdded) {
                    this.expense    = expense
                    this.position   = position
                    setTargetFragment(fragment, Helper.EXPENSE_CODE)
                    show(fragmentManager, "")
                }
            }
        }

    }

}