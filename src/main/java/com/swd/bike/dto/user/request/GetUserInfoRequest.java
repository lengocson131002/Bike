package com.swd.bike.dto.user.request;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRequest extends BaseRequestData {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
