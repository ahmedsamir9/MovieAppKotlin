
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.movieapp.utils.ApiResult
import com.example.movieapp.utils.Resources


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
abstract class NetworkBoundResource<CacheObj, NetworkObj> {

    fun asFlow() = flow {
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            when (val apiResponse = fetchFromNetwork()) {
                is ApiResult.Success -> {
                    saveNetworkResult(apiResponse.value)
                    emitAll(loadFromDb().map { Resources.success(it) })
                }
                is ApiResult.GenericError -> {
                    onFetchFailed()
                    emitAll(loadFromDb().map { Resources.error(apiResponse.errorMessage!!, it) })
                }
            }
        } else {
            emitAll(loadFromDb().map { Resources.success(it) })
        }
    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }


    protected abstract suspend fun saveNetworkResult(item: NetworkObj)


    protected abstract fun shouldFetch(data: CacheObj?): Boolean


    protected abstract fun loadFromDb(): Flow<CacheObj>

    protected abstract suspend fun fetchFromNetwork(): ApiResult<NetworkObj>
}
