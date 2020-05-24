package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simoes.expense.R
import com.simoes.expense.view.fragments.ExpenseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_expenses.setOnClickListener {
            btn_expenses.setBackgroundColor(resources.getColor(R.color.background_color_card_and_container))
            btn_history .setBackgroundColor(resources.getColor(R.color.background_color))
            btn_more    .setBackgroundColor(resources.getColor(R.color.background_color))

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ExpenseFragment())
            ft.commit()
        }

        btn_history.setOnClickListener {
            btn_expenses.setBackgroundColor(resources.getColor(R.color.background_color))
            btn_history .setBackgroundColor(resources.getColor(R.color.background_color_card_and_container))
            btn_more    .setBackgroundColor(resources.getColor(R.color.background_color))
        }

        btn_more.setOnClickListener {
            btn_expenses.setBackgroundColor(resources.getColor(R.color.background_color))
            btn_history .setBackgroundColor(resources.getColor(R.color.background_color))
            btn_more    .setBackgroundColor(resources.getColor(R.color.background_color_card_and_container))
        }


    }
}
