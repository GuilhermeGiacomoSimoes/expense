package com.simoes.expense.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import com.simoes.expense.R

class LoadingFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT) )
        dialog?.window?.setLayout            ( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    companion object {

        fun getInstance()  = LoadingFragment()

        fun showDialog(fragmentManager: FragmentManager) {
            with(getInstance()) {
                if(!isAdded) {
                    isCancelable = false
                    show(fragmentManager, "progress")
                }
            }
        }

        fun dismissLoading() {
            with(getInstance()) {
                if(isAdded) {
                    dismiss()
                }
            }

        }
    }
}
