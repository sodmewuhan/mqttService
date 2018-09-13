package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoxStatusMapper {

    /**
     * 增加盒子状态信息
     * @param boxStatus
     * @return
     */
    @Insert("insert into t_box_status(boxNumber,deviceId,status) " +
            "values(#{boxNumber},#{deviceId},#{status})")
    public int addBoxStatus(BoxStatus boxStatus);

    /**
     * 检查盒子&设备是否在线
     * @param boxStatus
     * @return
     */
    @Select("select count(1) from t_box_status where boxNumber = #{boxNumber} and deviceId=#{deviceId}")
    public int checkBoxDevice(BoxStatus boxStatus);

    /**
     * 查询BOXDevice的状态信息，APP使用
     * @param boxStatus
     * @return
     */
    @Select("select * from t_box_status where boxNumber = #{boxNumber}")
    public List<BoxStatus> getBoxDevice(BoxStatus boxStatus);

    @Update("update t_box_status set status = #{status}")
    public void updateBoxDevice(BoxStatus boxStatus);
}
