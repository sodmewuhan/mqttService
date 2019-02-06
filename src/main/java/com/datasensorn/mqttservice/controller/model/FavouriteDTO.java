package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

/**
 * 问题关注
 */
@Data
public class FavouriteDTO {

    // 问题的关注人数
    private Integer favouriteCount;

    // 问题ID
    private Integer questionId;

    // 是否已经关注
    private boolean isFavourite;

    // 传递参数，当前的用户
    private String username;

}
