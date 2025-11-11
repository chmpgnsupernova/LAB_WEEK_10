package com.example.lab_week_10
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase

class MainActivity : AppCompatActivity() {

    // Ini nih, kita panggil ViewModel-nya
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    // TAMBAHIN INI
    private val db by lazy { prepareDatabase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // KITA INISIALISASI DULU DARI DB
        initializeValueFromDatabase()
        // BARU SIAPIN VIEWMODEL
        setupViewModel() // (Nama fungsi ini di PDF 'prepareViewModel') [cite: 423]
    }
    override fun onPause() {
        super.onPause()
        // Nyimpen nilai terakhir dari ViewModel ke Database
        // 'ID' dapet dari companion object (Step 7)
        // '!!' dipake karena kita yakin 'total.value' gak null pas onPause
        db.totalDao().update(Total(ID, viewModel.total.value!!))
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
    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build() // 'allowMainThreadQueries' itu ga bagus,
        // tapi buat lab ini gpp [cite: 429-430, 435]
    }

    private fun initializeValueFromDatabase() {
        // Ambil data 'total' dari DB pake ID
        val total = db.totalDao().getTotal(ID) // 'ID' dari Step 7

        if (total.isEmpty()) {
            // Kalo DB-nya kosong (pertama kali app jalan)
            db.totalDao().insert(Total(id = 1, total = 0))
        } else {
            // Kalo DB-nya ada isinya, pake 'setTotal' yg kita bikin tadi
            viewModel.setTotal(total.first().total)
        }
    }

    companion object {
        const val ID: Long = 1
    }
}