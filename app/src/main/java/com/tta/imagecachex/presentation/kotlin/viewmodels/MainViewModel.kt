package com.tta.imagecachex.presentation.kotlin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.imagecachex.data.model.ImageDataEntity
import com.tta.imagecachex.data.repository.LocalRepositoryImpl
import com.tta.imagecachex.data.util.toDbDataEntity
import com.tta.imagecachex.domain.usecase.GetImageListUseCase
import com.tta.imagecachex.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val localRepositoryImpl: LocalRepositoryImpl,
) : ViewModel() {

    private val _imageState = MutableStateFlow<Resource<List<ImageDataEntity>>>(Resource.Loading)
    val imageList = _imageState.asStateFlow()

    init {
        getAllImagesFromDatabase()
    }

    private fun getAllImagesFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            localRepositoryImpl.getAllListItems().distinctUntilChanged()
                .collect { listOfEvents ->
                    if (listOfEvents.isEmpty()) {
                        _imageState.value = Resource.Failure("Empty Database")
                        getRemoteImageList()
                    } else {
                        _imageState.value = Resource.Success(listOfEvents)
                    }
                }
        }
    }

    fun getRemoteImageList() {
        viewModelScope.launch {
            getImageListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _imageState.value = Resource.Failure(it.message.toString())
                }
                .collect { response ->

                    response?.body()?.map { it.toDbDataEntity() }
                        ?.let { it1 ->
                            localRepositoryImpl.addAllListItems(it1)
                            if (it1.isNotEmpty()) {
                                getAllImagesFromDatabase()
                            }
                        }
                }
        }
    }


}