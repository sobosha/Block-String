package com.diaco.sample;

import java.util.ArrayList;
import java.util.List;

public class StoryModel implements Comparable<StoryModel> {
    String id;
    boolean seen=false;
    List<String> src= new ArrayList<>();
    String Type,Link="",Topic;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public List<String> getSrc() {
        return src;
    }

    public void setSrc(List<String> src) {
        this.src = src;
    }

    @Override
    public int compareTo(StoryModel o) {
        if(this.seen)
            return 1;
        else if(!this.seen){
            return -1;
        }
        else {
            return 0;
        }
    }
}
