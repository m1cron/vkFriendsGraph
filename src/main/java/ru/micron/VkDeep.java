package ru.micron;

public class VkDeep {
    private final Integer srcId;
    private final Integer destId;
    private final Integer deep;

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
}
