package com.nuist.bookms;

import com.nuist.bookms.service.UserPermissionService;
import com.nuist.bookms.service.impl.UserPermissionServiceImpl;
import org.junit.jupiter.api.Test;

public class TestUserPermissionService {
    UserPermissionService userPermissionService = new UserPermissionServiceImpl();

    @Test
    public void testSelectPermissionsByUserId() {
        System.out.println(userPermissionService.selectPermissionsByUserId(1));
    }

    @Test
    public void testHasPermission() {
        System.out.println(userPermissionService.hasPermission(1, "user:register"));
    }

}
