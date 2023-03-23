package com.deepak.movielisting.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.deepak.movielisting.listners.BaseView

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), BaseView {

    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!this::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (binding.root.parent as? ViewGroup)?.removeView(binding.root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    abstract override fun initViews()

    abstract override fun observeViewModel()
}