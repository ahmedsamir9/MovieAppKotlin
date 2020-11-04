package com.example.movieapp.ui.HomeScreen.states

sealed class HomeStateEvent {
    object PopularMovies: HomeStateEvent()
    object TopRatedMovies :HomeStateEvent()
    object UpComingMovies :HomeStateEvent()
    object PlayingNowMovies:HomeStateEvent()
}