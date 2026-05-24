package com.nuist.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RolePermission {
    private Integer roleId;
    private Integer permissionId;
}
