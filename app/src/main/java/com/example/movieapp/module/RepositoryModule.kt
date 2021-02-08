package com.example.movieapp.module

import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.repository.ActorRepository
import com.example.movieapp.repository.HomeRepository.HomeRepository
import com.example.movieapp.repository.MovieDetailsRepositry.MovieDetailsRepo
import com.example.movieapp.repository.Searchrepository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideHomeRepository( local:AppDatabase , remote :MoviesServices):HomeRepository = HomeRepository(local,remote)
    @Singleton
    @Provides
    fun provideMovieDetailsRepository( local:AppDatabase , remote :MoviesServices):MovieDetailsRepo =MovieDetailsRepo(local,remote)
    @Singleton
    @Provides
    fun provideSearchRepository( local:AppDatabase , remote :MoviesServices):SearchRepository =SearchRepository(local,remote)
    @Singleton
    @Provides
    fun provideActorRepository( local:AppDatabase , remote :MoviesServices): ActorRepository =
        ActorRepository(local,remote)
}