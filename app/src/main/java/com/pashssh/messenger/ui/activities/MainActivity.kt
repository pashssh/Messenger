package com.pashssh.messenger.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        mToolbar = binding.appBarMain.toolbar
        setSupportActionBar(mToolbar)
        initFirebase()

        initNavBar()


        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)




        if (AUTH.currentUser == null) {
            replaceActivity(RegistrationActivity())
        }
    }

    private fun initNavBar() {
        val headerImage =
            binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.nav_header_image)
        val headerName =
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_name)
        val headerPhone =
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_phone)
        REF_DATABASE.child(USERS_CHILD).child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                val user = snapshot.getValue(UserEntity::class.java)
                user?.let { entity ->
                    if (entity.photoUrl.isNotEmpty()) {
                        Picasso.get().load(entity.photoUrl).into(headerImage)
                    }
                    headerName.text = entity.username
                    headerPhone.text = entity.phone
                }
            })
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