package com.nkm.mypracticespring.dto.access;

import com.nkm.mypracticespring.dto.common.DataRequest;
import com.nkm.mypracticespring.exceptions.DataInvalidException;
import io.micrometer.common.util.StringUtils;
import lombok.Data;

@Data
public class SignupRequest extends DataRequest {

    private String name;

    private String password;

    private String email;

    @Override
    public void validate() throws DataInvalidException {
        if (StringUtils.isBlank(name) || name.length() < 8) {
            throw new DataInvalidException("Email invalid");
        }

        if (StringUtils.isBlank(email) || email.length() < 8) {
            throw new DataInvalidException("Email invalid");
        }

        if (StringUtils.isBlank(password) || password.length() < 8) {
            throw new DataInvalidException("Password invalid");
        }
    }
}
