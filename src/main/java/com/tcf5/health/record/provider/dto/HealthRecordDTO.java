package com.tcf5.health.record.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecordDTO {
    private Long id;
    private String patientId;
    private String resourceType;
    private UUID clientId;
    private String resourceContent;
    private LocalDateTime processedAt;
}

