package com.example.cinema.po;

import com.example.cinema.vo.PopularMovieVO;

public class PopularMoviePO {

    /**
     * 票房信息，即该电影近期所有票的总价
     */
    private double boxOffice;

    /**
     * 电影的名称
     */
    private String movieName;

    public PopularMoviePO(){
    }

    public PopularMoviePO(String name, double boxOffice){
        this.boxOffice=boxOffice;
        this.movieName=name;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public PopularMovieVO getVO(){
        return new PopularMovieVO(movieName,boxOffice);
    }

}
