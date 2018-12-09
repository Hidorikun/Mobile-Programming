package com.example.george.firstapp

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class NetworkAPIAdapter private constructor() {

    private object Holder {
        val INSTANCE = NetworkAPIAdapter()
    }

    companion object {
        val instance: NetworkAPIAdapter by lazy { Holder.INSTANCE }
        const val BASE_URL: String = "http://10.152.1.165:5000"
        private const val URL_ORDERS_ALL: String = "/books"
        private const val URL_ORDER_INDIVIDUAL: String = "/books/{id}"
    }

    private val bookService: BooksService

    init {
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetworkAPIAdapter.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        bookService = retrofit.create(BooksService::class.java)
    }

    fun fetchAll(): List<Book> {
        return bookService.fetchAll().execute().body()!!
    }

    fun insert(dto: Book): Observable<Book> {
        Log.d("INSERT", dto.toString())
        return bookService.insert(dto.title!!, dto.author!!, dto.description!!, dto.rating!!)
    }

    fun update(id: String, dto: Book): Observable<ResponseBody> {
        return bookService.update(id, dto.title!!, dto.author!!, dto.description!!, dto.rating!!)
    }

    fun delete(id: String): Observable<ResponseBody> {
        return bookService.delete(id)
    }

    interface BooksService {
        @GET(URL_ORDERS_ALL)
        fun fetchAll(): Call<List<Book>>

        @POST(URL_ORDERS_ALL)
        @FormUrlEncoded
        fun insert(@Field("title") title: String,
                   @Field("author") author: String,
                   @Field("description") description: String,
                   @Field("rating") rating: Int
        ): Observable<Book>

        @PUT(URL_ORDER_INDIVIDUAL)
        @FormUrlEncoded
        fun update(@Path("id") id: String,
                   @Field("title") title: String,
                   @Field("author") author: String,
                   @Field("description") description: String,
                   @Field("rating") rating: Int
        ): Observable<ResponseBody>

        @DELETE(URL_ORDER_INDIVIDUAL)
        fun delete(@Path("id") id: String): Observable<ResponseBody>
    }
}