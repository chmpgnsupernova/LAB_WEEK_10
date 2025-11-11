package com.example.lab_week_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        // Pake 'view?.' biar aman kalo view-nya null
        view?.findViewById<TextView>(R.id.text_total)?.text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        // Ini kuncinya: pake 'requireActivity()' biar dapet ViewModel
        // yang sama kayak 'Activity'
        val viewModel =
            ViewModelProvider(requireActivity()).get(TotalViewModel::class.java)

        // Kita 'observe' datanya. 'viewLifecycleOwner' biar aman
        viewModel.total.observe(viewLifecycleOwner) { total ->
            // Tiap data 'total' di ViewModel berubah, 'updateText' kepanggil
            updateText(total)
        }
    }
}