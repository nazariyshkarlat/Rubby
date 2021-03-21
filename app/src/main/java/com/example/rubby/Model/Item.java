package com.example.rubby.Model;

import com.example.rubby.OverridedWidgets.ExpandableRecyclerView.models.ExpandableGroup;

import java.util.List;

public class Item extends ExpandableGroup<SubItem> {

    public Item() {
        super();
    }

    public Item(List<SubItem> items) {
        super(items);
    }

}
