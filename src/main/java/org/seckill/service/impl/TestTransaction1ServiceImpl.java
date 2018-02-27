package org.seckill.service.impl;

import org.seckill.dao.SuccessKilledDao;
import org.seckill.service.TestTransaction1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yuezhang on 18/2/26.
 */
@Service
public class TestTransaction1ServiceImpl implements TestTransaction1Service {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Transactional
    public void testSubTransaction() {
        successKilledDao.insertSuccessKilled(1,13918648199L);
    }

    @Transactional
    public void testSubTransaction2() {
        successKilledDao.insertSuccessKilled(2,13918648199L);
    }
}
