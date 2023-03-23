package com.deepak.movielisting.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.deepak.movielisting.listners.BaseView

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), BaseView {

    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
        initViews()
        observeViewModel()
    }

    abstract override fun initViews()

    abstract override fun observeViewModel()

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}


