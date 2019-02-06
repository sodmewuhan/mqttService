package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.Favourite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavouriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favourite record);

    int insertSelective(Favourite record);

    Favourite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Favourite record);

    int updateByPrimaryKey(Favourite record);

    /**
     * 判断用户是否已经关注
     * @param reord
     * @return
     */
    Favourite isFavouriteByUsername(Favourite record);

    /**
     * 当前问题的关注人数
     * @param record
     * @return
     */
    int getFavouriteCount(Favourite record);
}