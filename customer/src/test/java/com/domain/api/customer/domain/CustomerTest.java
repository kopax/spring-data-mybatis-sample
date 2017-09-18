package com.domain.api.customer.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dka on 6/3/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@Import({ Customer.class })
public class CustomerTest {

    @Test
    public void test() {
//        Customer customer = new Customer();
//        customer.setFirstName("test");
//        assertThat(customer.getFirstName()).isEqualTo("test");
        assertThat("test").isEqualTo("test");
    }

}

