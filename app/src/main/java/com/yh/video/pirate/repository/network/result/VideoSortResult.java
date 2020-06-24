package com.yh.video.pirate.repository.network.result;

public class VideoSortResult {


    /**
     * id : 28
     * name : 國產自拍
     * icopath : http://image.hannlin.com/storage/cm/sysico/2020/06/08/159160738810000.png
     * order : 1
     */

    private int id;
    private String name;
    private String icopath;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcopath() {
        return icopath;
    }

    public void setIcopath(String icopath) {
        this.icopath = icopath;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
