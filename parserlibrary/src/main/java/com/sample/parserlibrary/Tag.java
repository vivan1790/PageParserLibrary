package com.sample.parserlibrary;

import android.view.View;

import java.util.ArrayList;

public class Tag {

    public String name;
    public ArrayList<TagAttribute> attributes;
    public View view;

    public Tag(String name, ArrayList<TagAttribute> attributes, View view) {
        this.name = name;
        this.attributes = attributes;
        this.view = view;
    }
}
