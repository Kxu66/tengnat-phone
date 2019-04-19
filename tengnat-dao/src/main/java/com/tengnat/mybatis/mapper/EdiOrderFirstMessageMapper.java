package com.tengnat.mybatis.mapper;

import com.tengnat.mybatis.entity.EdiOrderFirstMessage;
import com.tengnat.mybatis.model.EdiOrderFirstMessageModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface EdiOrderFirstMessageMapper extends BaseMapper<EdiOrderFirstMessage> {
    @ResultType(Integer.class)
    @Select("select count(fm_id) from edi_order_first_message o where  o.source_order_id=#{sourceOrderId} and o.target_order_id=#{targetOrderId}")
    int selectCountBySourceOrderIdAndTargetOrderId(@Param("sourceOrderId") Long sourceOrderId,@Param("targetOrderId")Long targetOrderId);
    @ResultType(List.class)
    @Select("select * from edi_order_first_message o where  (o.im_from_account = #{imFromAccount} and o.im_to = #{imTo})or(o.im_from_account = #{imTo} and o.im_to = #{imFromAccount})")
    List<EdiOrderFirstMessageModel> selectByToOrFromAccount(@Param("imFromAccount")String imFromAccount, @Param("imTo")String imTo);

}
