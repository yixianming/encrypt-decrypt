package com.example.encryptdecrypt.dto;

import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Data
public class EncAndDecReqDTO {
    private String iv;
    private String encDate;
}
