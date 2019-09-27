package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Activity;

import java.util.List;

public interface ActivityServiceForBL {

    List<Activity> selectActivitiesByMovie(int movieId);
}
