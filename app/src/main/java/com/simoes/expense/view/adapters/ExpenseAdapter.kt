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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val expense = listExpense[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layout = inflater.inflate(R.layout.expenses_adapter, null)

        if (position >= listExpense.size - 1 || position == 0) {
            layout = inflater.inflate(R.layout.bottom_or_top_list, null)
        }
        else {
            val txtNameExpense      = layout.findViewById<TextView>(R.id.txt_name_expense)
            txtNameExpense.text     = expense.name

            val txtDueDate          = layout.findViewById<TextView>(R.id.txt_due_date)
            txtDueDate.text         = expense.dueDate

            val txtValueExpense     = layout.findViewById<TextView>(R.id.txt_value_expense)
            txtValueExpense.text    = Helper.getValueMoney(expense.value).split("$")[1]

            val imgExpense          = layout.findViewById<ImageView>(R.id.img_expense)

            val imageCardOrMoney    = getImageCardOrMoney( expense )
            imgExpense.setImageResource( imageCardOrMoney )

            if ( Helper.expenseOwn( expense ) ) {
                if ( ! expense.paidOut ){
                    txtNameExpense.setTextColor(Color.parseColor("#FF0000"))
                }
                else {
                    txtNameExpense.setTextColor(Color.parseColor("#2d9b30"))
                }
            }
            else if(expense.paidOut) {
                txtNameExpense.setTextColor(Color.parseColor("#2d9b30"))
            }
            else {
                txtNameExpense.setTextColor(Color.parseColor("#000000"))
            }
        }

        return layout
    }

    override fun getItem(position: Int): Any {
        return listExpense[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listExpense.size
    }

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}