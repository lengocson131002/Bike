package com.swd.bike.dto.station.request;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.StationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStationStatusRequest extends BaseRequestData {
    private Long id;
    private StationStatus status;
}
