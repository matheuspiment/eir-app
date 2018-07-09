package br.ufg.inf.es.eir.model;

import java.io.Serializable;
import java.util.List;

public class Remedy implements Serializable {
    private int id;
    private String name;
    private String image;
    private List composition;
    private String type;
    private int content;
    private String lab;
    private int code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List getComposition() {
        return composition;
    }

    public void setComposition(List composition) {
        this.composition = composition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
