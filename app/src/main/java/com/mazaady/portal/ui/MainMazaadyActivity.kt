package com.mazaady.portal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mazaady.portal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_mazaady_portal.*

@AndroidEntryPoint
/* The MainMazaadyActivity class sets up the bottom navigation view with the navigation controller of
the navigation host fragment in the main Mazaady portal activity. */
class MainMazaadyActivity : AppCompatActivity() {

    /**
     * This function sets up the bottom navigation view with the navigation controller of the
     * navigation host fragment in the main Mazaady portal activity.
     *
     * @param savedInstanceState savedInstanceState is a parameter of the onCreate() method in Android
     * that represents the saved state of the activity. It is a Bundle object that contains data that
     * was saved by the activity before it was destroyed, such as the state of UI components or other
     * important data. This parameter is used to restore the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mazaady_portal)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }

}