package com.simoes.expense.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simoes.expense.R
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import java.util.*

class ExpenseAdapter(private var listExpense: ArrayList<Expense>, private var context: Context) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder ( layout : View ) : RecyclerView.ViewHolder( layout ) {
        val txtNameExpense  : TextView
        val txtDueDate      : TextView
        val txtValueExpense : TextView
        val imgExpense      : ImageView

        init {
            txtNameExpense      = layout.findViewById<TextView>(R.id.txt_name_expense)
            txtDueDate          = layout.findViewById<TextView>(R.id.txt_due_date)
            txtValueExpense     = layout.findViewById<TextView>(R.id.txt_value_expense)
            imgExpense          = layout.findViewById<ImageView>(R.id.img_expense)
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = listExpense[position]

        holder.txtNameExpense.text      = expense.name
        holder.txtDueDate.text          = expense.dueDate
        holder.txtValueExpense.text     = Helper.getValueMoney(expense.value).split("$")[1]
        val imageCardOrMoney            = Helper.getImageCardOrMoney( expense )
        holder.imgExpense.setImageResource( imageCardOrMoney )

        if ( Helper.expenseOwn( expense ) ) {
            if ( ! expense.paidOut ){
                holder.txtNameExpense.setTextColor(Color.parseColor("#FF0000"))
            }
            else {
                holder.txtNameExpense.setTextColor(Color.parseColor("#2d9b30"))
            }
        }
        else if(expense.paidOut) {
            holder.txtNameExpense.setTextColor(Color.parseColor("#2d9b30"))
        }
        else {
            holder.txtNameExpense.setTextColor(Color.parseColor("#000000"))
        }
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

        val holder = ViewHolder(layout)

    private fun getImageCardOrMoney( expense : Expense ) : Int {
        return when (expense.category) {
            TypeCategory.PUB -> {
                R.drawable.ic_pub
            }
            TypeCategory.SUPERMARKET -> {
                R.drawable.ic_supermarket
            }
            TypeCategory.ALCOHOLIC_BEVERAGES -> {
                R.drawable.ic_alcoholic_beverages
            }
            TypeCategory.TICKETS -> {
                R.drawable.ic_tickets
            }
            TypeCategory.FOOD -> {
                R.drawable.ic_food
            }
            TypeCategory.INTERNET -> {
                R.drawable.ic_internet
            }
            TypeCategory.WATER -> {
                R.drawable.ic_water
            }
            TypeCategory.ENERGY -> {
                R.drawable.ic_energy
            }
            TypeCategory.FURNITURE -> {
                R.drawable.ic_furniture
            }
            else -> {
                R.drawable.ic_other
            }
        }

        return holder
    }

    override fun getItemCount() = listExpense.size
}