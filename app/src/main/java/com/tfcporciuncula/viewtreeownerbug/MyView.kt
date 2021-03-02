package com.tfcporciuncula.viewtreeownerbug

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner

@Suppress("UNCHECKED_CAST")
class MyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

  private val viewModel by lazy {
    val factory = object : AbstractSavedStateViewModelFactory(findViewTreeSavedStateRegistryOwner()!!, null) {
      override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return MyViewModel() as T
      }
    }
    ViewModelProvider(findViewTreeViewModelStoreOwner()!!, factory).get(MyViewModel::class.java)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    viewModel.log()
  }
}

class MyViewModel : ViewModel() {

  fun log() = Log.d("MyViewModel", "${hashCode()}")
}
