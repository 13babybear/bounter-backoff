package cn.bounter.backoff.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 补偿记录
 */
@Data
@Accessors(chain = true)
public class BackoffRecord {

    private Long id;

    /**
     * 快照类名
     */
    private String className;

    /**
     * 快照方法名
     */
    private String methodName;

    /**
     * 快照参数
     */
    private String param;

    /**
     * 快照参数类型
     */
    private String paramType;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 补偿状态
     * @see cn.bounter.backoff.enums.BackoffStatusEnum
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

}
