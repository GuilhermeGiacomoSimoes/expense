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

    class ViewHolder ( layout : View , pos : Int , context: Context, listExpense: ArrayList<Expense>) : RecyclerView.ViewHolder( layout ) {

        var txtNameExpense  = TextView( context )
        var txtDueDate      = TextView( context )
        var txtValueExpense = TextView( context )
        var imgExpense      = ImageView( context )

        init {
            if (pos < listExpense.size - 1 && pos > 0) {
                txtNameExpense      = layout.findViewById<TextView>(R.id.txt_name_expense)
                txtDueDate          = layout.findViewById<TextView>(R.id.txt_due_date)
                txtValueExpense     = layout.findViewById<TextView>(R.id.txt_value_expense)
                imgExpense          = layout.findViewById<ImageView>(R.id.img_expense)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = listExpense[position]

        if ( position != 0 && position < listExpense.size - 1 ) {
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
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var holder = ViewHolder(inflater.inflate(R.layout.expenses_adapter, parent, false), viewType, context, listExpense)

        if (viewType >= listExpense.size - 1 || viewType == 0) {
            holder = ViewHolder(inflater.inflate(R.layout.bottom_or_top_list, parent, false), viewType, context, listExpense)
        }

        return holder
    }

    override fun getItemCount() = listExpense.size
}