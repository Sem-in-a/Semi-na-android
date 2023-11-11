package com.semina.semi_na.data.db.entity;

public enum Location {
    IT("정보관"),LAW("진리관"),JOMANSIK("조만식기념관"),ENGINEER("형남공학관"),BUSINESS("숭덕경상관");

    private String locName;
    Location(String locName) {
        this.locName = locName;
    }
    public String getLocName(){
        return locName;
    }
}
