package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository {
    static class Pair{
        String directorName;
        List<String> movieNames;

        public Pair(String directorName, List<String> movieNames) {
            this.directorName = directorName;
            this.movieNames = movieNames;
        }

        public String getDirectorName() {
            return directorName;
        }

        public void setDirectorName(String directorName) {
            this.directorName = directorName;
        }

        public List<String> getMovieNames() {
            return movieNames;
        }

        public void setMovieNames(List<String> movieNames) {
            this.movieNames = movieNames;
        }
    }
    Map<String,Movie> dbMovie = new HashMap<>();
    Map<String,Director> dbDirector = new HashMap<>();
    List<Pair> MovieDirectorPairs = new ArrayList<>();


    public String addMovie(Movie movie){
        String name = movie.getName();
        dbMovie.put(name,movie);
        return "Movie Added Successfully";
    }

    public String addDirector(Director director){
        String name = director.getName();
        dbDirector.put(name,director);
        MovieDirectorPairs.add(new Pair(name,new ArrayList<>()));
        return "Director Added Successfully";
    }

    public String addMovieDirectorPair(String movieName,String directorName){
        if(!dbMovie.containsKey(movieName)){
            return "Movie Doesn't Exist !";
        }
        if(!dbDirector.containsKey(directorName)){
            return "Director Doesn't Exist !";
        }

        for(Pair p : MovieDirectorPairs){
            if(p.getDirectorName().equals(directorName)){
                List<String> temp = p.getMovieNames();
                temp.add(movieName);
                p.setMovieNames(temp);
                break;
            }
        }

        return "Movie-Director Pair Added Successfully";
    }

    public Movie getMovieByName(String movieName){
        if(!dbMovie.containsKey(movieName)){
           return null;
        }
        return dbMovie.get(movieName);
    }

    public Director getDirectorByName(String directorName){
        if(!dbDirector.containsKey(directorName)){
            return null;
        }
        return dbDirector.get(directorName);
    }

    public List<String> getMoviesByDirectorName(String directorName){
       for(Pair p : MovieDirectorPairs){
           if(p.getDirectorName().equals(directorName)){
               return p.getMovieNames();
           }
       }
        return null;
    }

    public List<String> findAllMovies(){
        return new ArrayList<>(dbMovie.keySet());
    }

    public String deleteDirectorByName(String directorName){
        for(Pair p : MovieDirectorPairs){
            if(p.getDirectorName().equals(directorName)){
                MovieDirectorPairs.remove(p);
                dbDirector.remove(directorName);
                for(String s : p.getMovieNames()){
                    dbMovie.remove(s);
                }
                return "Record of director and its movies deletes successfully";
            }
        }
        return "director doesn't exist";
    }

    public String deleteAllDirectors(){
        for(Pair p : MovieDirectorPairs){
                MovieDirectorPairs.remove(p);
                dbDirector.remove(p.getDirectorName());
                for(String s : p.getMovieNames()){
                    dbMovie.remove(s);
                }
            }
        return "All Director and their Movies deleted";
    }
}
