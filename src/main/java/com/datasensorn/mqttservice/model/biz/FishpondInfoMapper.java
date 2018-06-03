package com.datasensorn.mqttservice.model.biz;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 鱼塘信息mapper
 */
@Mapper
public interface FishpondInfoMapper {

    /**
     * 添加鱼塘信息
     * @param fishpondInfo
     * @return
     */
    @Insert("insert into t_fishpond (poundName,poundAddress,poundArea,poundDeepth,breedType,breedName,counts,boxInfoId,userId) " +
            "values(#{poundName},#{poundAddress},#{poundArea},#{poundDeepth},#{breedType},#{breedName},#{counts},#{boxInfoId},#{userId})")
    public int addFishpond(FishpondInfo fishpondInfo);

    /**
     * 删除鱼塘
     * @param fishpondId
     * @return
     */
    @Delete("Delete from t_fishpond where id = #{0}")
    public int deleteFishpond(Integer fishpondId);



}
