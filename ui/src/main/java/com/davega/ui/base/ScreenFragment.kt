package com.davega.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.davega.ui.viewmodel.LoadingEvent
import com.davega.ui.theme.ArchitectureAndroidTheme
import com.davega.ui.utils.hideLoading
import com.davega.ui.utils.showLoading
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class ScreenFragment<V : ViewModel>(
    private val clazz: KClass<V>,
    private val navGraphId: Int? = null,
    fullScreen: Boolean = false,
    animation: Int? = null,
    isCancelable: Boolean = true
) : BaseFragment(
    fullScreen = fullScreen,
    animation = animation,
    isCancelable = isCancelable
) {

    protected val viewModel: V by lazy {
        if (navGraphId != null) {
            findNavController().getViewModelStoreOwner(navGraphId)
                .getViewModel(clazz = clazz, parameters = ::getParameters)
        } else {
            getViewModel(clazz = clazz, parameters = ::getParameters)
        }
    }

    open fun getParameters(): ParametersHolder {
        return parametersOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onInit()
        viewModel
        return ComposeView(requireContext()).apply {
            setContent {
                InitLoading()
                ArchitectureAndroidTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Screen()
                    }
                }
            }
        }
    }

    protected fun loading(isLoading: Boolean){
        viewModel.let { viewModel ->
            if(viewModel is LoadingEvent){
                viewModel.loading(isLoading)
            }
        }
    }

    protected fun loading(isLoading: Boolean, timeMillis: Long){
        viewModel.let { viewModel ->
            if(viewModel is LoadingEvent){
                viewModel.loading(isLoading, timeMillis)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.let { viewModel ->
            if(viewModel is LoadingEvent && !viewModel.isLoading()){
                loading(false)
                hideLoading()
            }
        }
    }

    @Composable
    private fun InitLoading(){
        viewModel.let { viewModel ->
            if(viewModel is LoadingEvent){
                viewModel.OnLoading {
                    if(it){
                        showLoading()
                    } else {
                        hideLoading()
                    }
                }
            }
        }
    }

}