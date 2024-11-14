package com.macaron.corpresent.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:08
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "资源")
@RequestMapping("/api/v1/category/resource")
public class ResourceCategoryController {



}
