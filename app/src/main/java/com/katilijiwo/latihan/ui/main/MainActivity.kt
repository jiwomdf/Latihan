package com.katilijiwo.latihan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.katilijiwo.latihan.R
import com.katilijiwo.latihan.base.BaseActivity
import com.katilijiwo.latihan.base.BaseViewModel
import com.katilijiwo.latihan.data.remote.exception.*
import com.katilijiwo.latihan.databinding.ActivityMainBinding
import com.katilijiwo.latihan.ui.main.adapter.FooterAdapter
import com.katilijiwo.latihan.ui.main.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.net.SocketTimeoutException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    MainViewModel::class.java
) {

    @Inject
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUsersRecyclerView()
        viewModel.getUsers("testing")
        viewModel.fetchUserByName("jiwo")
    }

    override fun setListener() {
        super.setListener()

        viewModel.users.observe(this, {
            usersAdapter.submitData(lifecycle, it)
        })

        viewModel.listUserByName.observe(this, {
            when(it){
                is BaseViewModel.UserEvent.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data.first().login, Toast.LENGTH_SHORT).show()
                }
                is BaseViewModel.UserEvent.Error -> {
                    showLoading(false)
                    getExceptionMessage(it.exception)
                }
                is BaseViewModel.UserEvent.Loading -> {
                    showLoading(true)
                }
            }
        })

    }

    private fun getExceptionMessage(exception: Exception) {
        when(exception) {
            is SocketTimeoutException -> {
                Toast.makeText(this, "SocketTimeoutException", Toast.LENGTH_SHORT).show()
            }
            is NoInternetException -> {
                Toast.makeText(this, "NoInternetException", Toast.LENGTH_SHORT).show()
            }
            is NotFoundException -> {
                Toast.makeText(this, "NotFoundException", Toast.LENGTH_SHORT).show()
            }
            is UnAuthorizedException -> {
                Toast.makeText(this, "UnAuthorizedException", Toast.LENGTH_SHORT).show()
            }
            is ForbiddenException -> {
                Toast.makeText(this, "ForbiddenException", Toast.LENGTH_SHORT).show()
            }
            is UnKnownException -> {
                Toast.makeText(this, "UnKnownException", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUsersRecyclerView(){
        binding.rvUsers.apply {
            adapter = usersAdapter.withLoadStateHeaderAndFooter(
                header = FooterAdapter({ usersAdapter.retry() },
                    { errMsg ->  Toast.makeText(this@MainActivity, errMsg, Toast.LENGTH_LONG).show()}),
                footer = FooterAdapter({ usersAdapter.retry() },
                    { errMsg -> Toast.makeText(this@MainActivity, errMsg, Toast.LENGTH_LONG).show()})
            )
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }


}