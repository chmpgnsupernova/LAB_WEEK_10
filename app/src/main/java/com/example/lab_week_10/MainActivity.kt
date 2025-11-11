package com.example.lab_week_10
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel // <-- Pastiin import ini bener

class MainActivity : AppCompatActivity() {

    // Ini nih, kita panggil ViewModel-nya
    private val viewModel by lazy { // <--- BENER
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Logikanya kita pindah ke fungsi ini
        // (di PDF namanya prepareViewModel[cite: 117], tapi isinya kita benerin)
        setupViewModel()
    }

    // Fungsi ini masih sama, buat update text
    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // Fungsi baru buat ngurusin ViewModel
    private fun setupViewModel() {
        // 1. Tampilkan nilai awal dari ViewModel
        // 'total' di sini ngambil dari 'var total' di TotalViewModel
        updateText(viewModel.total)

        // 2. Set listener buat button
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            // Panggil fungsi di ViewModel, bukan incrementTotal() lokal lagi
            // Ini manggil 'incrementTotal()' di TotalViewModel [cite: 102]
            viewModel.incrementTotal()
            // 3. Update text-nya pake data terbaru dari ViewModel
            updateText(viewModel.total)
        }
    }
}