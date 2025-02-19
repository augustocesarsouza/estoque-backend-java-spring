package com.estoque.backend;

import com.estoque.backend.applicationTest.AllServiceTest.UserAddressServiceTest;
import com.estoque.backend.applicationTest.AllServiceTest.UserAuthenticationServiceTest;
import com.estoque.backend.applicationTest.AllServiceTest.UserManagementServiceTest;
import com.estoque.backend.applicationTest.utilTest.BCryptPasswordEncoderUtilTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({UserManagementServiceTest.class, UserAuthenticationServiceTest.class,
        UserAddressServiceTest.class, BCryptPasswordEncoderUtilTest.class})
public class TestAllClass {
}
