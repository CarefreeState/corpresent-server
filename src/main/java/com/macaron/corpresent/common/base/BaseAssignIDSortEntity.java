package com.macaron.corpresent.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 10:35
 */
@Getter
@Setter
public class BaseAssignIDSortEntity extends BaseAssignIDEntity {

    /**
     * 排序字段
     */
    @TableField(value = "sort", fill = FieldFill.INSERT)
    private Double sort;

}
