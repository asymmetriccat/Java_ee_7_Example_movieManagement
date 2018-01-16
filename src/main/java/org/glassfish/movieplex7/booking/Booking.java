/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.movieplex7.booking;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import javax.persistence.PersistenceContext;
import org.glassfish.movieplex7.entities.Movie;
import org.glassfish.movieplex7.entities.ShowTiming;



/**
 *
 * @author jackli
 */
@Named
@FlowScoped("booking")
public class Booking {
    
    @PersistenceContext
    EntityManager em;
    private int movieId;
    private String startTime;
    private int startTimeId;

    public Booking() {
    }
    private static final Logger LOG = Logger.getLogger(Booking.class.getName());

    public Booking(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getStartTimeId() {
        return startTimeId;
    }

    public void setStartTime(String startTime) {
        StringTokenizer tokens=new StringTokenizer(startTime, ",");
        startTimeId=Integer.parseInt(tokens.nextToken());
        this.startTime = tokens.nextToken();
    }

    public void setStartTimeId(int startTimeId) {
        this.startTimeId = startTimeId;
    }
    
    public String getMoviesName(){
        try{
            return em.createNamedQuery("Movie.findById", Movie.class).setParameter("id", movieId).getSingleResult().getName();
        }catch(NoResultException e){
            return "";
        }
    }
    public String getTheater(){
        try{
            List<ShowTiming> list=em.createNamedQuery("ShowTiming.findByMovieAndTimeId", ShowTiming.class)
                    .setParameter("movieId", movieId)
                    .setParameter("timingId", startTimeId)
                    .getResultList();
            if(list.isEmpty()) return "none";
            return list.get(0).getTheaterId().getId().toString();
        }catch(NoResultException e){
            return "none";
        }
    }
    
}
