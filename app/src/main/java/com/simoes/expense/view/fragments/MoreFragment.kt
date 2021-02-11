package com.simoes.expense.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.simoes.expense.R
import com.simoes.expense.view.activitys.*
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openScreenCreateBank.setOnClickListener {
            startActivity(Intent( context, AddCardActivity ::class.java ))
        }

        openScreenAddCredit.setOnClickListener {
            startActivity(Intent( context, AddAmountActivity ::class.java ))
        }

        openScreenAddExpense.setOnClickListener {
            startActivity(Intent( context, AddExpenseActivity ::class.java ))
        }

        openListCards.setOnClickListener {
            startActivity(Intent( context, ListCardActivity::class.java ))
        }

        openTransf.setOnClickListener {
            startActivity(Intent( context, TransferActivity::class.java ))
        }
    }
}
