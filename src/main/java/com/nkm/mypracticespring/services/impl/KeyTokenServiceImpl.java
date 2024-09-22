package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.models.KeyTokenModel;
import com.nkm.mypracticespring.services.IKeyTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KeyTokenServiceImpl implements IKeyTokenService {


    @Override
    public KeyTokenModel findByUserId(String userId) {
        return null;
    }
}
