package com.hid16605093.movieflix.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

import datamodels.Movies.MovieSearchResults;

// Movie search results data wrapper class
public class MovieSearchResultsDataWrapper implements Serializable
{
   // Movie search results
    private ArrayList<MovieSearchResults> movieSearchResults;

    // Constructor
    public MovieSearchResultsDataWrapper(ArrayList<MovieSearchResults> data) { this.movieSearchResults = data; }

    // Get teh results
    public ArrayList<MovieSearchResults> getMovieSearchResults() { return this.movieSearchResults; }
}
