package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var reminderDao: RemindersDao



    // using idling resource to wait for the data to be loaded
    private lateinit var idlingResource: IdlingResource
    fun isIdlingResource(): IdlingResource? {
        return idlingResource
    }


    @Before
    fun initDb() {
        database =  idlingResource.let {
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RemindersDatabase::class.java
            ).allowMainThreadQueries().build()
        }
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveReminderAndGetById() = runBlockingTest {
        val reminder = ReminderDTO("title", "description", "location", 0.0, 0.0)
        reminderDao.saveReminder(reminder)

        val loaded = reminderDao.getReminderById(reminder.id)

        assertThat(loaded as ReminderDTO, notNullValue())
        assertThat(loaded.id, `is`(reminder.id))
        assertThat(loaded.title, `is`(reminder.title))
        assertThat(loaded.description, `is`(reminder.description))
        assertThat(loaded.location, `is`(reminder.location))
        assertThat(loaded.latitude, `is`(reminder.latitude))
        assertThat(loaded.longitude, `is`(reminder.longitude))
    }
    @Test
    fun deleteAllReminders() = runBlockingTest {
        val reminder = ReminderDTO("title", "description", "location", 0.0, 0.0)
        reminderDao.saveReminder(reminder)
        reminderDao.deleteAllReminders()

        val reminders = reminderDao.getReminders()

        assertThat(reminders.isEmpty(), `is`(true))
    }

    @Test
    fun getReminders() = runBlockingTest {
        val reminder = ReminderDTO("title", "description", "location", 0.0, 0.0)
        reminderDao.saveReminder(reminder)

        val reminders = reminderDao.getReminders()

        assertThat(reminders.isEmpty(), `is`(false))
        assertThat(reminders.size, `is`(1))
    }

    // reminder doesn't exist

    @Test
    fun reminderDoesNotExist() = runBlockingTest {
        val reminder = ReminderDTO("title", "description", "location", 0.0, 0.0)
        reminderDao.saveReminder(reminder)

        val loaded = reminderDao.getReminderById("2")

        assert(loaded == null)
    }


}