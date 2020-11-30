package com.baizhi.po;

import com.baizhi.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPO {

    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;
    //外键
    private Category categoryList;
}
