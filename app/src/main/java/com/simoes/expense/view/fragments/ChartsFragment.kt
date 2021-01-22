package com.simoes.expense.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
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

    private fun configGraphs(list: List<Card>) {
        val cardsAndValue   = java.util.ArrayList<String>()
        val arr = BarGraphSeries<DataPoint>()

        for ((index, card) in list.withIndex()) {
            val name = card.name
            var value = .0

            for (expense in card.expenses) {
                value += expense.value
            }

            val x = .0 + index
            arr.appendData(DataPoint(x, value), true, Integer.MAX_VALUE)
            cardsAndValue.add(name)
        }

        graph_bank.addSeries(arr)

        arr.isDrawValuesOnTop   = true
        arr.valuesOnTopColor    = Color.BLUE

        graph_bank.viewport.isScrollable        = true
        graph_bank.viewport.isYAxisBoundsManual = true
        graph_bank.viewport.isXAxisBoundsManual = true

        graph_bank.gridLabelRenderer.isHorizontalLabelsVisible = true
        graph_bank.viewport.setMinY(0.0)
        graph_bank.gridLabelRenderer.textSize = 40f
        graph_bank.gridLabelRenderer.reloadStyles()
        graph_bank.title = "Gastos por banco no mes"

        arr.spacing    = 1

        val staticLabelsFormatter = StaticLabelsFormatter(graph_bank)
        staticLabelsFormatter.setHorizontalLabels(cardsAndValue.toArray( arrayOf() ))

        graph_bank.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }

    private fun searchAllCard() {
        CRUDController.findAll(Card(), fragmentManager!!, this, context!!)
    }

    override fun callback(list: ArrayList<Any>) {
        val listCard = list as List<Card>
        configGraphs(listCard)
    }
}