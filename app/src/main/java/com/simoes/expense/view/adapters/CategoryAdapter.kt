package com.simoes.expense.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.simoes.expense.R
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.TypeCategory
import kotlin.collections.ArrayList

class CategoryAdapter(private var context: Context, private var list: ArrayList<TypeCategory>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layout = if ( convertView == null ) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.category_adapter, null)
        } else {
            convertView
        }

        val image   = layout.findViewById<ImageView>(R.id.categoryImage)
        val text    = layout.findViewById<TextView>(R.id.categoryName)

        if (Helper.getIconTypeCategory( getItem(position).toString() ) != null){
            image.setImageResource( Helper.getIconTypeCategory( getItem(position).toString() ) !! )
        }

        if (Helper.getTranslateExpenseTypePortuguese( getItem(position).toString() ) != null) {
            text.text = Helper.getTranslateExpenseTypePortuguese( getItem(position).toString() )
        }

        return layout
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}