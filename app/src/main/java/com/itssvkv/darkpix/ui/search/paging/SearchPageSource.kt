package com.itssvkv.darkpix.ui.search.pagingimport androidx.paging.PagingSourceimport androidx.paging.PagingStateimport com.itssvkv.darkpix.data.network.ApiCallsimport com.itssvkv.darkpix.models.responses.PixelsPhotosResponseimport javax.inject.Injectclass SearchPageSource @Inject constructor(    private val apiCalls: ApiCalls,    private val searchText: String) : PagingSource<Int, PixelsPhotosResponse.Photo>() {    override fun getRefreshKey(state: PagingState<Int, PixelsPhotosResponse.Photo>) = null    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixelsPhotosResponse.Photo> {        return try {            val pageIndex = params.key ?: 1            val response = apiCalls.searchForItem(searchText, pageIndex)            LoadResult.Page(                data = response.photos,                nextKey = if (response.photos.isEmpty()) null else pageIndex + 1,                prevKey = if (pageIndex == 1) null else pageIndex - 1            )        } catch (e: Exception) {            LoadResult.Error(e)        }    }}