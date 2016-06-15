package br.ufg.extensao.espacodasprofissoes.model;

/**
 * Created by marceloquinta on 15/06/16.
 */
public class Course {

    private String name;

    private int id;

    private int regionalId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(int regionalId) {
        this.regionalId = regionalId;
    }
}
