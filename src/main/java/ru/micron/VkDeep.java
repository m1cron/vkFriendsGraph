package ru.micron;

public class VkDeep {
    private Integer srcId;
    private Integer destId;
    private Integer deep;

    public VkDeep(Integer srcId, Integer destId, Integer deep) {
        this.srcId = srcId;
        this.destId = destId;
        this.deep = deep;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public Integer getDestId() {
        return destId;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public void setDestId(Integer destId) {
        this.destId = destId;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }
}
