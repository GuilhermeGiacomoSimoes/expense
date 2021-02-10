package com.simoes.expense.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.DateHelper
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import com.simoes.expense.model.models.Wallet
import kotlinx.android.synthetic.main.expense_detail_dialog.*
import java.text.SimpleDateFormat

class ExpenseDetailDialog : DialogFragment(), CallBackReturn {

    private lateinit var expense  : Expense
    private var position = 0
    private var statusRequest = ""
    private var wallet : Wallet? = null

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
        verifyRepeat()
        constructListPaid()
    }

    private fun constructListPaid() {
        if ( context != null && expense.datePaid.size > 0 ) {
            progressbar_expense_detail.visibility = View.GONE
            list_paid_expense.visibility = View.VISIBLE

            list_paid_expense.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, expense.datePaid)
        } else {
            list_paid_expense.visibility = View.GONE
            progressbar_expense_detail.visibility = View.GONE
        }
    }

    private fun verifyRepeat() {
        if ( expense.repeat ) {
            if( SimpleDateFormat("dd/MM/yyyy").parse(expense.dueDate).time < DateHelper.nowMilliseconds()) {
                expense.dueDate = addAMonthToTheDate(expense.dueDate)
                expense.paidOut = false
                if ( context != null && fragmentManager != null ) {
                    CRUDController.update(expense, fragmentManager!!, context!!, this)
                }else if(fragmentManager != null){
                    FeedbackDialog.showDialog(fragmentManager!!, "Erro ao atualizar o banco", "Erro")
                }
            }
        }
    }

    private fun addAMonthToTheDate(dueDate : String) : String {
        val dateSplit  = dueDate.split("/")
        var month = Integer.parseInt(dateSplit[1])
        var year =  Integer.parseInt(dateSplit[2])

        if( month > 11 ){
            month = 1
            year ++

        } else {
            month ++
        }

        return "${dateSplit[0]}/${month}/${year}"
    }

    private fun configureTexts() {
        title_expense_dialog.text = expense.name
        value_expense_dialog.text = expense.value.toString()
        date_expense_dialog.text  = expense.dueDate.toString()
    }

    private fun pay( amount : Double , expense : Expense ) {
        if (expense.value < amount) {
            txt_paid_out.visibility                 = View.GONE
            loading_paidout.visibility              = View.VISIBLE
            btn_payment_expense_dialog.isEnabled    = false
            statusRequest = "Pago com sucesso"

            expense.paidOut = true
            CRUDController.update( expense, fragmentManager!!, context!! , this)

            val intent = Intent()
            intent.putExtra(Helper.EXPENSE_RETURN, expense)

            targetFragment?.onActivityResult(Helper.PAYMENT_EXPENSE, Activity.RESULT_OK, intent)
        } else {
            FeedbackDialog.showDialog(fragmentManager!!, "Você não tem saldo suficiente para efetuar o pagamento", "Saldo insuficiente")
        }
    }

    private fun configureButtons() {
        btn_payment_expense_dialog.setOnClickListener {
            if ( fragmentManager != null && context != null) {
                val amount = when {
                    expense.card != null -> {
                        expense.card!!.balance
                    }
                    wallet != null -> {
                        wallet?.amount
                    }
                    else -> {
                        .0
                    }
                }

                pay( amount!!, expense )
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

        fun showDialog( fragmentManager: FragmentManager, expense: Expense , position : Int, fragment : Fragment, wallet : Wallet?) {
            with(instance) {
                if (!isAdded) {
                    this.wallet     = wallet
                    this.expense    = expense
                    this.position   = position
                    setTargetFragment(fragment, Helper.EXPENSE_CODE)
                    show(fragmentManager, "")
                }
            }
        }
    }

    override fun callback(list: ArrayList<Any>) {
        return
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