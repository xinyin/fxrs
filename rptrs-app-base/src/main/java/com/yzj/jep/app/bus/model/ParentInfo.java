package com.yzj.jep.app.bus.model;

import java.util.List;

public class ParentInfo {
    private String name;
    private String id;

    private List<ParentInfo> _parents;

    public List<ParentInfo> get_parents() {
        return _parents;
    }

    public void set_parents(List<ParentInfo> _parents) {
        this._parents = _parents;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
