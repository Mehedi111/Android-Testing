package com.sarik.androidtesting.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sarik.androidtesting.R
import com.sarik.androidtesting.data.local.ShoppingDao
import com.sarik.androidtesting.data.local.ShoppingItemDatabase
import com.sarik.androidtesting.others.Constants.BASE_URL
import com.sarik.androidtesting.others.Constants.DATABASE_NAME
import com.sarik.androidtesting.repositories.DefaultShoppingRepository
import com.sarik.androidtesting.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Mehedi Hasan on 11/23/2020.
 */

@Module
@InstallIn(ApplicationComponent::class) // install module as long as the application class
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.getShoppingDao()


    @Singleton
    @Provides
    fun providePixaBayApi(): PixabayAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayAPI::class.java)

    @Singleton
    @Provides
    fun provideDeafultShoppingRepository(
        dao: ShoppingDao,
        pixabayAPI: PixabayAPI
    ) =  DefaultShoppingRepository(dao, pixabayAPI) as ShoppingRepository


    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_image)
    )
}