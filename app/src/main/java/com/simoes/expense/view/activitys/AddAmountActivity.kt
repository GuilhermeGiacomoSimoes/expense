package com.simoes.expense.view.activitys

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.NameClasses
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Wallet
import com.simoes.expense.view.fragments.FeedbackDialog
import kotlinx.android.synthetic.main.activity_add_amount.*

class AddAmountActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listCards          : ArrayList<Card>
    private lateinit var cardSelected       : Card
    private var isLoadingCardsAndWallet     = false
    private var isWallet                    = false
    private var count                       = 0
    private lateinit var wallet             : Wallet
    private val names                       = arrayListOf("Carteira")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amount)

        findCardsAndWallet()
        inflateListBank()

        btn_add_amount_bank.setOnClickListener {
            if( checkIfTheMandatoryFieldsAreFilled() ) {
                txt_add_amount.visibility       = View.GONE
                loading_add_amount.visibility   = View.VISIBLE
                btn_add_amount_bank.isEnabled   = false

                addAmountBank()
                clearScreen()
            } else {
                FeedbackDialog.showDialog(supportFragmentManager, "Favor preencher campos obrigatórios", "Campos obrigatórios em branco")
            }
        }

        list_bank_for_add_amount.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView          : AdapterView<*>?,
                selectedItemView    : View?,
                position            : Int,
                id                  : Long
            ) {
                if (position == 1) {
                    isWallet = true
                } else {
                    isWallet = false
                    cardSelected = listCards[position - 1]
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                //TODO implements
            }
        }
    }

    private fun findCardsAndWallet() {
        CRUDController.findAll( Card(), supportFragmentManager , this, this)
        CRUDController.findAll( Wallet(), supportFragmentManager, this, this )
    }

    private fun checkIfTheMandatoryFieldsAreFilled() : Boolean {
        return edt_amount_add.text?.isNotEmpty() == true
    }

    private fun clearScreen() {
        edt_amount_add.text?.clear()
    }

    private fun inflateListBank() {
        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            names
        )

        list_bank_for_add_amount.adapter = adapter
    }

    private fun getListBankName(listCard: ArrayList<Card> ) : ArrayList<String> {

        for ( bank in listCard ) {
            names.add( bank.name )
        }

        return names
    }

    private fun addAmountBank() {
        val edtAmount   =  edt_amount_add.text.toString().toDouble()

        if( isWallet ) {
            wallet.amount += edtAmount
            CRUDController.update( wallet, supportFragmentManager, this , this)
        }
        else{
            val bank        = cardSelected
            bank.balance    += edtAmount

            CRUDController.update( bank, supportFragmentManager, this , this)
        }
    }

    override fun callback(isSuccess: Boolean) {
        if ( isSuccess ) {
            txt_add_amount.visibility       = View.VISIBLE
            loading_add_amount.visibility   = View.GONE
            btn_add_amount_bank.isEnabled   = true
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show()
        }
    }

    override fun callback(list: ArrayList<Any>){
        if ( ! isLoadingCardsAndWallet ) {
            count ++
            if ( ! list.isNullOrEmpty() ) {
                if (list[0].javaClass.name == NameClasses.Card.toString()) {
                    listCards           = list as ArrayList<Card>
                    getListBankName(list)
                    inflateListBank()
                }
                else {
                    this.wallet = list[0] as Wallet
                }
            }

            isLoadingCardsAndWallet = count > 1
        }
    }
}