package com.udacity.project4.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivityViewModel : ViewModel() {
    private val _user = MutableLiveData<FirebaseAuth>()
    val user : LiveData<FirebaseAuth>
        get() = _user

    private val _navigateToRemindersActivity = MutableLiveData<Boolean>()
    val navigateToRemindersActivity : LiveData<Boolean>
        get() = _navigateToRemindersActivity

    init {
        _user.value = FirebaseAuth.getInstance()
        _navigateToRemindersActivity.value = false
    }

    fun checkUser() : Boolean {
        return if (_user.value?.currentUser != null) {
            _navigateToRemindersActivity.value = true
            true
        } else {
            _navigateToRemindersActivity.value = false
            false
        }
    }

}