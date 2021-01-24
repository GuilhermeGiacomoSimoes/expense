package com.simoes.expense.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
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

    private fun configGraphs(list: ArrayList<Card>) {
        val visitors = ArrayList<PieEntry>()

        for (card in list){
            for (expense in card.expenses){
                visitors.add(PieEntry(expense.value.toFloat(), card.name))
            }
        }

        val pieDataSet = PieDataSet(visitors, "")
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        val pieData = PieData( pieDataSet )

        cardSpend.data = pieData
        cardSpend.description.isEnabled = true
        cardSpend.centerText = "Visitors"
        cardSpend.animate()
        cardSpend.invalidate()
    }

    private fun searchAllCard() {
        CRUDController.findAll(Card(), fragmentManager!!, this, context!!)
    }

    override fun callback(list: ArrayList<Any>) {
        val listCard = list as ArrayList<Card>
        configGraphs(listCard)
    }
}