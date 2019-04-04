package com.hjhl.customer.modal.luck;

import com.hjhl.customer.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnsScoreControl extends BaseEntity<AnsScoreControl> {

    /** 分数. */
    private Integer score;
    
    /** 活动id. */
    private String luckDrawId;
    
    /** 创建时间. */
    private String createTime;

    public AnsScoreControl() {
    }

    public AnsScoreControl(String luckDrawId) {
        this.luckDrawId = luckDrawId;
    }
}
