package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simoes.expense.R
import com.simoes.expense.view.fragments.ChartsFragment
import com.simoes.expense.view.fragments.ExpenseFragment
import com.simoes.expense.view.fragments.MoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_expenses.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ExpenseFragment())
            ft.commit()
        }

        btn_history.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ChartsFragment())
            ft.commit()
        }

        btn_more.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, MoreFragment())
            ft.commit()
        }
    }

    override fun onResume() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, MoreFragment())
        ft.commit()
        super.onResume()
    }
}
