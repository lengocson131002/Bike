package com.swd.bike.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseController {

    @Autowired
    protected SpringBus springBus;

    protected <T extends RequestData, I extends ResponseData> ResponseEntity<ResponseBase<I>> execute(T request) {
        return ResponseEntity.ok(new ResponseBase<>(this.springBus.execute(request)));
    }
}
