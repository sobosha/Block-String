package com.diaco.sample.Story;

import java.util.ArrayList;
import java.util.List;

public class ResponseStories {
    List<Stories> stories=new ArrayList<>();

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }
}
