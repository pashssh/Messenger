package com.pashssh.messenger.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.ActivityMainBinding
import com.pashssh.messenger.utils.*
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        mToolbar = binding.appBarMain.toolbar
        setSupportActionBar(mToolbar)

        val header = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.nav_header_image)
        Picasso.get()
            .load("https://cs12.pikabu.ru/post_img/2021/09/28/9/1632842735144728919.webp")
            .placeholder(R.drawable.ic_contact_placeholder)
            .into(header)


        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


        initFirebase()

        if (AUTH.currentUser == null) {
            replaceActivity(RegistrationActivity())
        }
    }

    override fun onResume() {
        super.onResume()
        REF_DATABASE.child(USERS_CHILD).child(CURRENT_UID).child("state").setValue(true)
    }

    override fun onPause() {
        super.onPause()
        REF_DATABASE.child(USERS_CHILD).child(CURRENT_UID).child("state").setValue(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AUTH.signOut()
                this.recreate()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

}