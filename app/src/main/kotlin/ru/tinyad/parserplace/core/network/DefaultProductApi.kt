package ru.tinyad.parserplace.core.network

import javax.inject.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinyad.parserplace.core.network.model.NetworkCreateOrEditSubscription
import ru.tinyad.parserplace.core.network.model.NetworkResponse
import ru.tinyad.parserplace.core.network.model.NetworkPreviewAndParameters

class DefaultProductApi @Inject constructor(private val http: HttpClient) : ProductApi {

    override suspend fun getPreviewAndParameters(urls: Array<String>): Flow<NetworkResponse<List<NetworkPreviewAndParameters>>> =
        flow {
            val response = http.post("product/preview_and_parameters") {
                setBody(mapOf("urls" to urls))
            }
            emit(response.body())
        }
//        flow {
//            emit(Result.Loading)
//
//            try {
//                val response = http.post("/api/product/preview_and_parameters") {
//                    setBody(urls)
//                }
//                val body: PreviewAndParameters = response.body()
//                println(">>> $body")
//                emit(Result.Success(body))
//            } catch (e: Exception) {
//                e.printStackTrace()
//                emit(Result.Error(e))
//            }
//        }

    override suspend fun saveSubscription(body: NetworkCreateOrEditSubscription) {
        http.post("product/subscription") {
            setBody(body)
        }
    }
}
