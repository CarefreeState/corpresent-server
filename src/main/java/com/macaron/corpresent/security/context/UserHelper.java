package com.macaron.corpresent.security.context;

import lombok.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-11
 * Time: 23:10
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHelper {

    private Long userId;

    private String username;

}
