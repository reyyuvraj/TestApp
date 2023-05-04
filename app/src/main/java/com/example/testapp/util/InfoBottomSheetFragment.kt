package com.example.testapp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.testapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheetFragment : BottomSheetDialogFragment() {

    private val args: InfoBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.info_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.ibs_title).text = args.title ?: "--"
        view.findViewById<TextView>(R.id.ibs_info).text = args.info ?: "--"
    }
}