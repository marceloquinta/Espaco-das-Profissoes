package br.ufg.extensao.espacodasprofissoes.model;

import java.util.List;

/**
 * Created by marceloquinta on 06/06/16.
 */
public class Route {

    private String name;
    private String color;
    private List<String> points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }
}
