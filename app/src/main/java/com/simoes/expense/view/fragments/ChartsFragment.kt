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
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.fragment_charts.*

class ChartsFragment : Fragment(), CallBackReturn {

    private var iRequested = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iRequested= true
        searchAllCard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configGraphs(list: ArrayList<Card>) {
        val listMonth  = getCardsMonth( list )

        configGraphCardSpend(listMonth)
        configGraphSpenderByCategory(listMonth)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCardsMonth(list : ArrayList<Card> ) : ArrayList<Card> {
        val listReturn = ArrayList<Card>()

        for (card in list) {
            val expenses = ArrayList<Expense>()

            for (expense in card.expenses) {
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

            card.expenses.removeAll(card.expenses)
            card.expenses = expenses
        }

        return listReturn
    }

    private fun configGraphSpenderByCategory(list: ArrayList<Card>) {
        val banks   = ArrayList<BarEntry>()
        val cards   = ArrayList<BarDataSet>()

        val categorys = HashMap<String, Float>()

        for (card in list) {
            for (expense in card.expenses){
                val value = categorys[expense.category.name]
                if (value != null) {
                    categorys[expense.category.name] = value + expense.value.toFloat()
                }
                else {
                    categorys[expense.category.name] = expense.value.toFloat()
                }
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

    private fun configGraphCardSpend(list: ArrayList<Card>) {
        val visitors = ArrayList<PieEntry>()

        for (card in list){
            var value = 0.0
            for (expense in card.expenses){
                value += expense.value
            }

            visitors.add(PieEntry(value.toFloat(), card.name))
        }

        val pieDataSet = PieDataSet(visitors, "")
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

    private fun searchAllCard() {
        CRUDController.findAll(Card(), fragmentManager!!, this, context!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun callback(list: ArrayList<Any>) {
        if (iRequested) {
            iRequested = false
            val listCard = list as ArrayList<Card>
            configGraphs(listCard)
        }
    }
}