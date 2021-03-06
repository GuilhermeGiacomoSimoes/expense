package com.simoes.expense.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.simoes.expense.R
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import com.simoes.expense.model.models.Wallet
import com.simoes.expense.view.fragments.ExpenseDetailDialog
import java.util.*

class ExpenseAdapter(private var listExpense: ArrayList<Expense>, private var context: Context, private val fragmentManager : FragmentManager, private val fragment: Fragment, private val wallet: Wallet?) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder ( layout : View , pos : Int , context: Context, listExpense: ArrayList<Expense>) : RecyclerView.ViewHolder( layout ) {

        var txtNameExpense  = TextView( context )
        var txtDueDate      = TextView( context )
        var txtValueExpense = TextView( context )
        var imgExpense      = ImageView( context )
        var layoutExpenseAdapter = LinearLayout( context )

        init {
            if (pos < listExpense.size - 1 && pos > 0) {
                txtNameExpense          = layout.findViewById(R.id.txt_name_expense)
                txtDueDate              = layout.findViewById(R.id.txt_due_date)
                txtValueExpense         = layout.findViewById(R.id.txt_value_expense)
                imgExpense              = layout.findViewById(R.id.img_expense)
                layoutExpenseAdapter    = layout.findViewById(R.id.layout_expense_adapter)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = listExpense[position]

        if ( position != 0 && position < listExpense.size - 1 ) {

            holder.layoutExpenseAdapter.setOnClickListener {
                ExpenseDetailDialog.showDialog( fragmentManager, expense, position, fragment, wallet)
            }

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