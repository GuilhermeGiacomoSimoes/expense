package com.simoes.expense.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Card
import kotlinx.android.synthetic.main.fragment_charts.*

class ChartsFragment : Fragment(), CallBackReturn {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAllCard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    private fun configGraphs( list: java.util.ArrayList<Card> )  {
        val banks = ArrayList<BarEntry>()

        for ((index, card) in list.withIndex()){
            var value = 0f

            for (expense in card.expenses){
                value += expense.value.toFloat()
            }

            banks.add( BarEntry( index + .0f, value ) )
        }
        
        val barDataSet = BarDataSet(banks, "banks")
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        barChart.setFitBars(true)
        barChart.data = barData
        barChart.animateY(2000)
    }

    private fun searchAllCard() {
        CRUDController.findAll(Card(), fragmentManager!!, this, context!!)
    }

    override fun callback(list: ArrayList<Any>) {
        val listCard = list as ArrayList<Card>
        configGraphs(listCard)
    }

    private fun hexToRGB(hex : Int): Array<Int> {
        return arrayOf( getRed(hex), getGreen(hex), getBlue(hex) )
    }
    private fun getRed(hex : Int) = hex shr 16 and 0xFF

    private fun getGreen(hex : Int) = hex shr 8 and 0xFF

    private fun getBlue(hex : Int) = hex and 0xFF

}