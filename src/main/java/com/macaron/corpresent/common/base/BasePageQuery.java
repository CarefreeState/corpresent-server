package com.macaron.corpresent.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.macaron.corpresent.common.constants.MyBatisPageConstants.*;


/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-21
 * Time: 13:31
 */
@Getter
@Setter
//@Schema(description = "分页查询参数")
public class BasePageQuery {

    @Schema(description = "当前页")
    private Long current;

    @Schema(description = "页内大小")
    private Long pageSize;

    @Schema(description = "排序字段")
    @JsonIgnore
    private String sortBy;

    @Schema(description = "是否正序")
    @JsonIgnore
    private Boolean isAsc;

    private void init() {
        current = Optional.ofNullable(current).orElse(DEFAULT_CURRENT);
        pageSize = Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE);
        sortBy = Optional.ofNullable(sortBy).orElse(DEFAULT_SORT_BY);
        isAsc = Optional.ofNullable(isAsc).orElse(DEFAULT_IS_ASC);
    }


    private static OrderItem buildOrderItem(String column, boolean asc) {
        return (new OrderItem()).setColumn(column).setAsc(asc);
    }

    public <T> IPage<T> toMpPage(OrderItem... orders){
        // 初始化
        init();
        // 1.分页条件
        Page<T> page = Page.of(current, pageSize);
        page.addOrder(buildOrderItem(sortBy, isAsc));
        // 2.排序条件
        List<OrderItem> orderItemList = Arrays.stream(orders)
                .filter(Objects::nonNull)
                .filter(order -> Objects.nonNull(order.getColumn()))
                .toList();
        if(!CollectionUtils.isEmpty(orderItemList)) {
            page.addOrder(orderItemList);
        }
        return page;
    }

    public <T> IPage<T> toMpPage(String sortBy, boolean isAsc){
        return toMpPage(buildOrderItem(sortBy, isAsc));
    }

}
