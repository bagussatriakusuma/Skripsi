package com.example.ecommerceskripsi.Repository

import com.example.ecommerceskripsi.Data.api.main.ProductAPI
import com.example.ecommerceskripsi.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.ecommerceskripsi.Data.api.main.buyerProduct.ProductResponse
import com.example.ecommerceskripsi.Data.api.main.sellerProduct.SellerProductResponse
import com.example.ecommerceskripsi.Data.api.main.sellerUploadProduct.UploadProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductAPI) {
    suspend fun getCategory(): Response<CategoryResponse> {
        return api.getCategory()
    }

    suspend fun getProductBuyer(status: String, categoryId: String, search: String)
            : Response<ProductResponse> {
        return api.getProductBuyer(status = status, categoryId = categoryId, search = search)
    }

    suspend fun uploadProductSeller(
        token: String,
        file : MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
        location: RequestBody,
    ): Response<UploadProductResponse> {
        return api.uploadProduct("Bearer $token" , file, name, description, base_price, categoryIds, location)
    }

    suspend fun getSellerProduct(token: String): Response<SellerProductResponse> {
        return api.getSellerProduct(token = "Bearer $token")
    }
}