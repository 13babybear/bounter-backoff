package cn.bounter.backoff.enums;

/**
 * 补偿状态枚举
 */
public enum BackoffStatusEnum {

    /** 已补偿 */
    Y(1, "已补偿"),
    /** 未补偿 */
    N(2, "未补偿");

    private int value;

    private String name;

    private BackoffStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 自定义方法
     * 根据枚举值获取枚举名称
     * @param value
     * @return
     */
    public static String nameOf(int value) {
        for(BackoffStatusEnum oneEnum : BackoffStatusEnum.values()) {
            if(oneEnum.value == value) {
                return oneEnum.getName();
            }
        }
        return null;
    }

}
