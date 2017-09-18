package com.domain.api.company.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dka on 6/3/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import({ Company.class })
public class CompanyTest {

    @Test
    public void test() {
        Company company = new Company();
        company.setName("test");
        assertThat(company.getName()).isEqualTo("test");
    }

}
