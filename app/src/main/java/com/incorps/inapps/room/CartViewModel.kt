package com.incorps.inapps.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    val getRentalList: LiveData<List<Rental>>
    val getDesainList: LiveData<List<Desain>>
    val getCetakList: LiveData<List<Cetak>>
    val getInstallList: LiveData<List<Install>>
    private val cartRepository: CartRepository

    init {
        val rentalDao = CartDatabase.getInstance(application).rentalDao()
        val desainDao = CartDatabase.getInstance(application).desainDao()
        val cetakDao = CartDatabase.getInstance(application).cetakDao()
        val installDao = CartDatabase.getInstance(application).installDao()
        cartRepository = CartRepository(rentalDao, desainDao, cetakDao, installDao)
        getRentalList = cartRepository.getRentalList
        getDesainList = cartRepository.getDesainList
        getCetakList = cartRepository.getCetakList
        getInstallList = cartRepository.getInstallList
    }

    // Rental
    fun insertRental(rental: Rental) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertRental(rental)
        }
    }

    fun updateRental(rental: Rental) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateRental(rental)
        }
    }

    fun deleteRental(rental: Rental) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteRental(rental)
        }
    }

    fun deleteRentalById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteRentalById(id)
        }
    }

    // Desain
    fun insertDesain(desain: Desain) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertDesain(desain)
        }
    }

    fun updateDesain(desain: Desain) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateDesain(desain)
        }
    }

    fun deleteDesain(desain: Desain) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteDesain(desain)
        }
    }

    fun deleteDesainById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteDesainById(id)
        }
    }

    // Cetak
    fun insertCetak(cetak: Cetak) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertCetak(cetak)
        }
    }

    fun updateCetak(cetak: Cetak) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateCetak(cetak)
        }
    }

    fun deleteCetak(cetak: Cetak) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCetak(cetak)
        }
    }

    fun deleteCetakById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCetakById(id)
        }
    }

    // Install
    fun insertInstall(install: Install) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertInstall(install)
        }
    }

    fun updateInstall(install: Install) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateInstall(install)
        }
    }

    fun deleteInstall(install: Install) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteInstall(install)
        }
    }

    fun deleteInstallById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteInstallById(id)
        }
    }
}