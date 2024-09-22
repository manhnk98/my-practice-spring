package com.nkm.mypracticespring.dto.access;

import com.nkm.mypracticespring.dto.common.DataRequest;
import com.nkm.mypracticespring.exceptions.DataInvalidException;
import io.micrometer.common.util.StringUtils;
import lombok.Data;

@Data
public class LoginRequest extends DataRequest {

    private String email;

    private String password;

    private String refreshToken;

    @Override
    public void validate() throws DataInvalidException {
        if (StringUtils.isBlank(email) || email.length() < 8) {
            throw new DataInvalidException("Email invalid");
        }

        if (StringUtils.isBlank(password) || password.length() < 8) {
            throw new DataInvalidException("Password invalid");
        }
    }
}
