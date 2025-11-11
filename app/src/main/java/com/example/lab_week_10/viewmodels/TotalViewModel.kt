package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    // 1. _total: Ini MutableLiveData, cuma bisa diubah dari DALEM ViewModel
    // Ini 'private' biar aman
    private val _total = MutableLiveData<Int>()

    // 2. total: Ini LiveData (read-only), buat diliatin sama Activity/Fragment
    // Ini 'public'
    val total: LiveData<Int> = _total

    // 3. 'init' dijalanin pas ViewModel pertama kali dibuat
    init {
        _total.postValue(0) // Set nilai awal 0 [cite: 235-240]
    }

    // 4. Fungsi increment-nya sekarang ngubah nilai si _total
    fun incrementTotal() {
        // Ambil nilai sekarang, tambahin 1, terus post lagi
        _total.postValue(_total.value?.plus(1))
    }
}