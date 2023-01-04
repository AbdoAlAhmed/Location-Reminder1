package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    private lateinit var saveReminderViewModel: SaveReminderViewModel
    private lateinit var fakeDataSource: FakeDataSource

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    val listData = List<ReminderDataItem>(1) {
        ReminderDataItem("title", "description"
            , "location", 0.0, 0.0) }


    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun saveReminder() {
        saveReminderViewModel.saveReminder(listData[0])
        assert(saveReminderViewModel.showLoading.value == false)
    }

    @Test
    fun validateAndSaveReminder() {
        saveReminderViewModel.validateAndSaveReminder(listData[0])
        assert(saveReminderViewModel.showLoading.value == false)
    }

    @Test
    fun onClear() {
        saveReminderViewModel.onClear()
        assert(saveReminderViewModel.reminderTitle.value == null)
        assert(saveReminderViewModel.reminderDescription.value == null)
        assert(saveReminderViewModel.reminderSelectedLocationStr.value == null)
        assert(saveReminderViewModel.selectedPOI.value == null)
        assert(saveReminderViewModel.latitude.value == null)
        assert(saveReminderViewModel.longitude.value == null)
    }




}