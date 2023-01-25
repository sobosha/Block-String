package com.diaco.sample.Story;

public class Stories implements Comparable<Stories> {
    String id;
    boolean Seen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setSeen(boolean seen) {
        Seen = seen;
    }

    @Override
    public int compareTo(Stories o) {
        if(this.Seen)
            return 1;
        else if(!this.Seen){
            return -1;
        }
        else {
            return 0;
        }
    }
}
