package org.seckill.service.impl;

import org.seckill.dao.SuccessKilledDao;
import org.seckill.service.TestTransaction2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yuezhang on 18/2/26.
 */
@Service
public class TestTransaction2ServiceImpl implements TestTransaction2Service {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Transactional
    public void testSubTransaction() {
        successKilledDao.insertSuccessKilled(2,13918648199L);
        if (true){
            throw new RuntimeException();
        }
    }
}
