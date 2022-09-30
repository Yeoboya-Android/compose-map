package kr.co.inforexseoul.core_data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.inforexseoul.core_data.usecase.GetPageDistrictUseCase
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

class DistrictPagingSource @Inject constructor(
    private val keyword: String,
    private val getPageDistrictUseCase: GetPageDistrictUseCase
) : PagingSource<Int, District>() {

    private companion object {
        private const val PAGING_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, District>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, District> {
        val position = params.key ?: PAGING_INDEX
        val loadData = getPageDistrictUseCase.invoke(keyword, position, params.loadSize)

        return LoadResult.Page(
            data = loadData,
            prevKey = if (position == PAGING_INDEX) null else position - 1,
            nextKey = if (loadData.isEmpty()) null else position + 1
        )
    }

}