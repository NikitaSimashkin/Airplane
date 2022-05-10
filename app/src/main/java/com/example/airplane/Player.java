package com.example.airplane;

public class Player implements  Comparable<Player>{
    private String nickname;
    private Integer number;
    private Integer points;
    private String id;

    public Player(String nickname, String id, Integer number, Integer points) {
        this.nickname = nickname;
        this.id = id;
        this.number = number;
        this.points = points;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void addNumber(Integer number) {
        this.number += number;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Player() {
    }

    @Override
    public int compareTo(Player o) {
        return this.getNumber() - o.getNumber();
    }
}
