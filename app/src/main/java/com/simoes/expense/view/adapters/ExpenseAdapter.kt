package com.simoes.expense.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.simoes.expense.R
import com.simoes.expense.model.models.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(private var listExpense: ArrayList<Expense>, private var context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val expense = listExpense[position]
        val layout : View

        layout = if ( convertView == null ) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.expenses_adapter, null)
        } else {
            convertView
        }

        val txtNameExpense      = layout.findViewById<TextView>(R.id.txt_name_expense)
        txtNameExpense.text     = expense.name

        val txtDueDate          = layout.findViewById<TextView>(R.id.txt_due_date)
        txtDueDate.text         = getDueData( expense.dueDate )

        val txtValueExpense     = layout.findViewById<TextView>(R.id.txt_value_expense)
        txtValueExpense.text    = expense.value.toString()

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

    private fun getDueData( dueDay : String ) : String {
        val dateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date        = Date()
        val now = dateFormat.format(date).toString()

        val nowArray = now.split("/")

        val month   = nowArray[1]
        val year    = nowArray[2]

        return "$dueDay/$month/$year"
    }

}