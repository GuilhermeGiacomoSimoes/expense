package com.simoes.expense.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.expense_detail_dialog.*
import java.util.ArrayList

class ExpenseDetailDialog : DialogFragment(), CallBackReturn {

    private lateinit var expense  : Expense
    private var position = 0
    private var statusRequest = ""
    private var amount = .0

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
                txt_paid_out.visibility                 = View.GONE
                loading_paidout.visibility              = View.VISIBLE
                btn_payment_expense_dialog.isEnabled    = false
                statusRequest = "Pago com sucesso"

                expense.paidOut = true
                CRUDController.update( expense, fragmentManager!!, context!! , this)

                val intent = Intent()
                intent.putExtra(Helper.EXPENSE_RETURN, expense)

                targetFragment?.onActivityResult(Helper.PAYMENT_EXPENSE, Activity.RESULT_OK, intent)
            }
        }

        btn_delete_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null ) {
                txt_delete_expense.visibility          = View.GONE
                loading_delete.visibility              = View.VISIBLE
                btn_delete_expense_dialog.isEnabled    = false
                statusRequest = "Deletado com sucesso"

                CRUDController.delete( expense,  fragmentManager!!, context!!, this)

                val intent = Intent()
                intent.putExtra(Helper.EXPENSE_RETURN, expense)

                targetFragment?.onActivityResult(Helper.DELETE_EXPENSE, Activity.RESULT_OK, intent)
            }
        }
    }

    companion object {
        var instance = ExpenseDetailDialog()

        fun showDialog( fragmentManager: FragmentManager, expense: Expense , position : Int, fragment : Fragment, amount : Double ) {
            with(instance) {
                if (!isAdded) {
                    this.expense    = expense
                    this.position   = position
                    setTargetFragment(fragment, Helper.EXPENSE_CODE)
                    show(fragmentManager, "")
                    this.amount = amount
                }
            }
        }
    }

    override fun callback(list: ArrayList<Any>) {
        TODO("Not yet implemented")
    }

    override fun callback(isSuccess: Boolean) {
        if ( isSuccess ) {
            txt_paid_out.visibility                 = View.VISIBLE
            loading_paidout.visibility              = View.GONE
            btn_payment_expense_dialog.isEnabled    = true

            txt_delete_expense.visibility          = View.VISIBLE
            loading_delete.visibility              = View.GONE
            btn_delete_expense_dialog.isEnabled    = true

            Toast.makeText(context, statusRequest, Toast.LENGTH_LONG).show()
        }

        dismiss()
    }
}