package com.example.mybuddygym.database.di

import android.app.Application
import android.content.Context
import com.example.mybuddygym.home.HomeFragment
import com.example.mybuddygym.signUp.SignUpFragment


import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        RepoBinding::class,
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder
    }

    // Asset Manager -- region start
    fun injectHomeFragment(homeFragment: SignUpFragment)

}