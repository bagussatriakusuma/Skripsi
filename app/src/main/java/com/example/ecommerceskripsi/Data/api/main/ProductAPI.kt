package com.example.ecommerceskripsi.Data.api.main

import com.example.ecommerceskripsi.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.ecommerceskripsi.Data.api.main.buyerProduct.ProductResponse
import com.example.ecommerceskripsi.Data.api.main.sellerProduct.SellerProductResponse
import com.example.ecommerceskripsi.Data.api.main.sellerUploadProduct.UploadProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {
    @GET("seller/category")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("buyer/product")
    suspend fun getProductBuyer(
        @Query("status") status: String,
        @Query("category_id") categoryId: String,
        @Query("search") search: String
    ): Response<ProductResponse>

    @Multipart
    @POST("seller/product")
    suspend fun uploadProduct(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_id") categoryId: List<Int>,
        @Part("location") location: RequestBody?,
    ): Response<UploadProductResponse>

    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("Authorization") token: String
    ): Response<SellerProductResponse>

}