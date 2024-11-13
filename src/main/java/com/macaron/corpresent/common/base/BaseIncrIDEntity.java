package com.macaron.corpresent.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <span>
 * classes that inherit this entity all have auto-incrementing ID.
 * </span>
 *
 */
@Getter
@Setter
public class BaseIncrIDEntity implements Serializable {

    /**
     * id, incr
     */
    @TableId(type = IdType.AUTO, value = "id")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Long createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Long updateTime;

    /**
     * 乐观锁
     */
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;

    /**
     * 是否禁用
     */
    @TableField(value = "is_blocked", fill = FieldFill.INSERT)
    private Boolean isBlocked;

}
