package com.simple.common.entity.user;

import com.simple.common.entity.role.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: []
 * Name: UserVO
 * Author: ws
 * Date: 2019/6/29 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer id;

    @ApiModelProperty("用户名")
    @Size(groups = {Update.class, Insert.class}, min = 4, max = 15, message = "用户名长度需在5-15之间")
    private String username;

    @ApiModelProperty("密码")
    @Size(min = 8, max = 15, message = "密码长度需在8-15之间")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码需要包含字母和数字")
    private String password;
    private Boolean remember;

    @ApiModelProperty("全名")
    @Size(groups = {Update.class, Insert.class}, min = 2, max = 15, message = "全称长度需在2-15之间")
    private String fullName;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("介绍")
    @Size(min = 2, max = 15, message = "个人介绍长度需在50个字符以内")
    private String introduction;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("角色")
    @NotEmpty
    private List<Integer> roleIds;
    private List<Role> roles;
    private String creator;
    private LocalDateTime createdDate;
    private String lastUpdater;
    private LocalDateTime lastUpdateDate;

    // 状态 0 禁用 1 启用
    @ApiModelProperty("状态 0 禁用 1 启用")
    private String status;
}
