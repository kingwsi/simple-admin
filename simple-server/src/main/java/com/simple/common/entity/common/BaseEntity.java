package com.simple.common.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description: 实体超类<br>
 * Comments Name: BaseEntity<br>
 * Date: 2019/7/12 18:08<br>
 * Author: ws<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    Integer creator;

    LocalDateTime createdDate;

    Integer lastUpdater;

    LocalDateTime lastUpdateDate;

    @TableLogic
    Boolean deleted;
}
