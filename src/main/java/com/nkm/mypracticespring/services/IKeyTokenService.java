package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.models.KeyTokenModel;

public interface IKeyTokenService {

    KeyTokenModel findByUserId(String userId);

}
