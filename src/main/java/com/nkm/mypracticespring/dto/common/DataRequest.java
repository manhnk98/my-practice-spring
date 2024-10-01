package com.nkm.mypracticespring.dto.common;

import com.nkm.mypracticespring.exceptions.DataRequestInvalidException;

public abstract class DataRequest {
    public abstract void validate() throws DataRequestInvalidException;
}
