package io.github.caesiumfox.web4.rest;

import java.util.List;

public class PostDataRequest {
    private List<Double> x;
    private Double y;
    private List<Double> r;

    public PostDataRequest() {}
    public PostDataRequest(List<Double> x, Double y, List<Double> r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public List<Double> getX() {
        return x;
    }
    
    public Double getY() {
        return y;
    }

    public List<Double> getR() {
        return r;
    }
}
