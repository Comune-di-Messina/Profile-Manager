package it.almaviva.eai.pm.shared.dto.permission;

import lombok.Data;

@Data
public class PermissionDTO {


    private String description;

    private Integer id;

    private String matchrule;

    private String httpmethod;

}
