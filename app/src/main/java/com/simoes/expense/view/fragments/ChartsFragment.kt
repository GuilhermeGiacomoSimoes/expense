package com.simoes.expense.view.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.fragment_charts.*

class ChartsFragment : Fragment(), CallBackReturn {

    private var iRequested = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_loading_charts.visibility = View.VISIBLE
        swipe_refresh_loading_charts.isRefreshing = true

        iRequested= true
        searchAllExpense()

        swipe_refresh_loading_charts.setOnRefreshListener {
            iRequested= true
            searchAllExpense()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configGraphs(list: ArrayList<Expense>) {
        val listMonth  = getExpenseMonth( list )

        configGraphCardSpend(listMonth)
        configGraphSpenderByCategory(listMonth)

        view_graphs.visibility = View.VISIBLE
        swipe_refresh_loading_charts.isRefreshing = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getExpenseMonth(list : ArrayList<Expense> ) : ArrayList<Expense> {
        val expenses = ArrayList<Expense>()

        for (expense in list) {
            val dateSplit = expense.date.split(" ")[0].split("/")
            val year = dateSplit[0]
            val month = dateSplit[1]

            val dateNowSplit = Helper.dateNow().split(" ")[0].split("/")
            val yearNow = dateNowSplit[0]
            val monthNow = dateNowSplit[1]

            if (year == yearNow && month == monthNow) {
                expenses.add(expense)
            }
        }

        return expenses
    }

    private fun configGraphSpenderByCategory(list: ArrayList<Expense>) {
        val banks       = ArrayList<BarEntry>()
        val cards       = ArrayList<BarDataSet>()
        val categorys   = HashMap<String, Float>()

        for (expense in list) {
            val value = categorys[expense.category.name]
            if (value != null) {
                categorys[expense.category.name] = value + expense.value.toFloat()
            }
            else {
                categorys[expense.category.name] = expense.value.toFloat()
            }
        }

        var index = 0
        for ((key, value) in categorys){
            banks.add( BarEntry( index + .0f, value) )

            val barDataSet = BarDataSet(banks, Helper.getTranslateExpenseTypePortuguese(key))

            if (Helper.getColorByTypeCategory(key) != null){
                barDataSet.color = Helper.getColorByTypeCategory(key) !!
            }

            barDataSet.valueTextColor = Color.BLACK
            barDataSet.valueTextSize = 16f

            cards.add(barDataSet)

            index ++
        }

        val barData = BarData(cards.toList())

        spenderByCategory.setFitBars(true)
        spenderByCategory.data = barData
        spenderByCategory.animateY(2000)
    }

    private fun indexOfString( list : ArrayList<String>, value : String ) : Int {
        for ( (i, v) in list.withIndex() ) {
            if( v == value )  return i
        }

        return -1
    }

    private fun configGraphCardSpend(list: ArrayList<Expense>) {
        val data        = ArrayList<PieEntry>()
        val cardsAdd    = ArrayList<String>()
        val cardsValue  = ArrayList<Float>()
        val cardsName   = ArrayList<String>()

        for (expense in list){
            val value = expense.value
            if (expense.card != null) {
                val index = indexOfString(cardsAdd, expense.card!!.uuid)
                if (index != -1) {
                    cardsValue[index] += value.toFloat()
                } else {
                    cardsValue.add(value.toFloat())
                    cardsName.add(expense.card!!.name)
                    cardsAdd.add(expense.card!!.uuid)
                }
            } else {
                val index = indexOfString(cardsAdd, "especie")
                if (index != -1) {
                    cardsValue[index] += value.toFloat()
                } else {
                    cardsValue.add(value.toFloat())
                    cardsName.add("Espécie")
                    cardsAdd.add("especie")
                }
            }
        }

        for ((i,v) in cardsValue.withIndex()) {
            data.add(PieEntry(v, cardsName[i]))
        }

        val pieDataSet = PieDataSet(data, "")
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        val pieData = PieData( pieDataSet )

        cardSpend.data = pieData
        cardSpend.description.isEnabled = true
        cardSpend.animate()
        cardSpend.animateXY(2000, 2000)
        cardSpend.invalidate()
    }

    private fun searchAllExpense() {
        CRUDController.findAll(Expense(), fragmentManager!!, this, context!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun callback(list: ArrayList<Any>) {
        if (iRequested) {
            if ( ! list.isNullOrEmpty()) {
                view_charts.visibility = View.VISIBLE
                val listExpense = list as ArrayList<Expense>
                configGraphs(listExpense)
            }
            else {
                swipe_refresh_loading_charts.isRefreshing = false
                txt_not_expenses_charts.visibility = View.VISIBLE
            }

            iRequested = false
        }
    }

    override fun callback(isSuccess: Boolean) {
        TODO("Not yet implemented")
    }
}