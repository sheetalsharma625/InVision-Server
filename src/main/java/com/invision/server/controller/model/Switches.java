package com.invision.server.controller.model;

import com.invision.server.model.SwitchDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Switches {
    List<SwitchDetails> switchDetails;
}
