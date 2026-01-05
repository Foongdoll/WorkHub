package com.foongdoll.portfolio.backend.auth.service;

import com.foongdoll.portfolio.backend.auth.dto.SignupDto;
import com.foongdoll.portfolio.backend.core.util.common.BaseRequest;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;

public interface AuthService {
    <T> T authenticate(BaseRequest<T> req);

    BaseResponse<Void> signup(BaseRequest<SignupDto> req);

    <T> T verify(String token);
}
