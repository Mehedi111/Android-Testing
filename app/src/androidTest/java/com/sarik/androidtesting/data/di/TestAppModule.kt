package com.sarik.androidtesting.data.di

import android.content.Context
import androidx.room.Room
import com.sarik.androidtesting.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

/**
 * Created by Mehedi Hasan on 11/25/2020.
 */

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    // we want to create new instance on every new test cases, so we dont use singleton
    @Named("test_db")
    @Provides
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}