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
        // 1. Kita 'observe' data 'total'
        // 'this' nunjuk ke 'Activity' sebagai 'LifecycleOwner'
        viewModel.total.observe(this) { total ->
            // Tiap data 'total' di ViewModel berubah, 'updateText' kepanggil
            updateText(total)
        }

        // 2. Set listener buat button
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            // Kita CUMA manggil incrementTotal().
            // Gak perlu 'updateText' manual lagi!
            // LiveData bakal ngurusin itu otomatis.
            viewModel.incrementTotal()
        }
    }
}