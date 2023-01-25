package com.diaco.sample.Story;

import java.util.ArrayList;
import java.util.List;

public class ResponseItemStory {
    List<itemStory> itemStories=new ArrayList<>();

    public List<itemStory> getItemStories() {
        return itemStories;
    }

    public void setItemStories(List<itemStory> itemStories) {
        this.itemStories = itemStories;
    }
}
