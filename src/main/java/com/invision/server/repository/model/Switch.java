package com.invision.server.repository.model;

import com.invision.server.model.SwitchState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Switch {

    @Id
    private String id;

    private SwitchState state;
    private String ipAddress;
    private String name;
    private Long timeStamp;
}
