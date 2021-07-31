package com.incorps.inapps.room

import androidx.lifecycle.LiveData

class CartRepository(
    private val rentalDao: RentalDao,
    private val desainDao: DesainDao,
    private val cetakDao: CetakDao,
    private val installDao: InstallDao
) {

    val getRentalList: LiveData<List<Rental>> = rentalDao.getRentalList()
    val getDesainList: LiveData<List<Desain>> = desainDao.getDesainList()
    val getCetakList: LiveData<List<Cetak>> = cetakDao.getCetakList()
    val getInstallList: LiveData<List<Install>> = installDao.getInstallList()

    // Rental
    fun insertRental(rental: Rental) {
        rentalDao.insertRental(rental)
    }

    fun updateRental(rental: Rental) {
        rentalDao.updateRental(rental)
    }

    fun deleteRental(rental: Rental) {
        rentalDao.deleteRental(rental)
    }

    fun deleteRentalById(id: Int) {
        rentalDao.deleteById(id)
    }

    // Desain
    fun insertDesain(desain: Desain) {
        desainDao.insertDesain(desain)
    }

    fun updateDesain(desain: Desain) {
        desainDao.updateDesain(desain)
    }

    fun deleteDesain(desain: Desain) {
        desainDao.deleteDesain(desain)
    }

    fun deleteDesainById(id: Int) {
        desainDao.deleteById(id)
    }

    // Cetak
    fun insertCetak(cetak: Cetak) {
        cetakDao.insertCetak(cetak)
    }

    fun updateCetak(cetak: Cetak) {
        cetakDao.updateCetak(cetak)
    }

    fun deleteCetak(cetak: Cetak) {
        cetakDao.deleteCetak(cetak)
    }

    fun deleteCetakById(id: Int) {
        cetakDao.deleteById(id)
    }

    // Install
    fun insertInstall(install: Install) {
        installDao.insertInstall(install)
    }

    fun updateInstall(install: Install) {
        installDao.updateInstall(install)
    }

    fun deleteInstall(install: Install) {
        installDao.deleteInstall(install)
    }

    fun deleteInstallById(id: Int) {
        installDao.deleteById(id)
    }
}