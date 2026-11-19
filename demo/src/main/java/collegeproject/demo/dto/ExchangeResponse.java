package collegeproject.demo.dto;

import collegeproject.demo.entity.Exchange;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExchangeResponse {
    private Long id;
    private String type;
    private String skill;
    private String description;
    private String partnerName;
    private LocalDateTime createdAt;

    public static ExchangeResponse from(Exchange e) {
        if (e == null) return null;
        ExchangeResponse r = new ExchangeResponse();
        r.setId(e.getId());
        r.setType(e.getType());
        r.setSkill(e.getSkill());
        r.setDescription(e.getDescription());
        r.setPartnerName(e.getPartnerName());
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }
}