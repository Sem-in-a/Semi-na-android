package com.semina.semi_na.data.db.entity;

public enum Location {
    IT("정보관"),Law("진리관"),Jomansik("조만식기념관"),Engineer("형남공학관"),Business("숭덕경상관");

    private String locName;
    Location(String locName) {
        this.locName = locName;
    }
    public String getLocName(){
        return locName;
    }
}
