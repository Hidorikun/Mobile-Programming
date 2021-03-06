package com.gecopuf.george.goodreads

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.gecopuf.george.goodreads.auth.AccountActivity
import com.gecopuf.george.goodreads.auth.LoginActivity
import com.gecopuf.george.goodreads.book.BookListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.app_bar_user.*
import kotlinx.android.synthetic.main.nav_header.*

class UserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //get firebase auth instance
        auth = FirebaseAuth.getInstance()

        authListener = FirebaseAuth.AuthStateListener {
            val userAuth = FirebaseAuth.getInstance().currentUser
            if (userAuth == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(this@UserActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val user = FirebaseAuth.getInstance().currentUser
        accountUsername.text = user!!.displayName
        accountEmail.text = user.email
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_all -> {
                val intent = Intent(this, BookListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_school_book_logout -> {
                signOut()
            }
            R.id.nav_settings -> {

            }

            R.id.nav_account -> {
                startActivity(Intent(this@UserActivity, AccountActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //sign out method
    private fun signOut() {
        auth.signOut()
    }

    public override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }


    public override fun onStop() {
        super.onStop()
        if (authListener != null) {
            auth.removeAuthStateListener(authListener)
        }
    }
}
