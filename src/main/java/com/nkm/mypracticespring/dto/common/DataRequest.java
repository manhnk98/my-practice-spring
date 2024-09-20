package com.nkm.mypracticespring.dto.common;

import com.nkm.mypracticespring.exceptions.DataInvalidException;

public abstract class DataRequest {
    public abstract void validate() throws DataInvalidException;
}
