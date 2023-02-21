package com.messages.abdallah.mymessages.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.messages.abdallah.mymessages.R
import com.messages.abdallah.mymessages.ViewModel.MsgsTypesViewModel
import com.messages.abdallah.mymessages.ViewModel.MsgsViewModel
import com.messages.abdallah.mymessages.ViewModel.MyViewModelFactory
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.databinding.ActivityMainBinding
import com.messages.abdallah.mymessages.db.LocaleSource
import com.messages.abdallah.mymessages.repository.MsgsRepo
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MsgsTypesViewModel
    lateinit var viewModel2: MsgsViewModel

    private lateinit var navController: NavController
    var mprogressdaialog: Dialog? = null
    var fragment = 1

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.atoolbar)

        navController =
            findNavController(R.id.nav_host_fragment_activity_main) //Initialising navController


        val retrofitService = ApiService.provideRetrofitInstance()
        val mainRepository = MsgsTypesRepo(retrofitService, LocaleSource(this))
        val mainRepository2 = MsgsRepo(retrofitService, LocaleSource(this))
        //  supportActionBar?.hide()

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(mainRepository, mainRepository2, this)).get(
                MsgsTypesViewModel::class.java
            )
        viewModel2 =
            ViewModelProvider(this, MyViewModelFactory(mainRepository, mainRepository2, this)).get(
                MsgsViewModel::class.java
            )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.refreshPosts(this)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showprogressdialog() {

        binding.progressBar.visibility = View.VISIBLE

      //  mprogressdaialog = Dialog(this)
      //  mprogressdaialog!!.setCancelable(false)
      //  mprogressdaialog!!.setContentView(R.layout.progress_dialog)

      //  mprogressdaialog!!.show()
    }

    fun hideprogressdialog() {
        Log.e("tesssst","entred")
        //  recreate()
       // mprogressdaialog!!.dismiss()
        binding.progressBar.visibility = View.GONE
        recreate()

    }

    override fun onDestroy() {
        if (mprogressdaialog != null && mprogressdaialog!!.isShowing) mprogressdaialog!!.dismiss()
        super.onDestroy()
    }

    override fun onStop() {
        //  if (mprogressdaialog != null && mprogressdaialog!!.isShowing) mprogressdaialog!!.dismiss()
        super.onStop()
    }

    override fun onDetachedFromWindow() {
        //  if (mprogressdaialog != null && mprogressdaialog!!.isShowing) mprogressdaialog!!.dismiss()

        super.onDetachedFromWindow()
    }



}