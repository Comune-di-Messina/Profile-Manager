package it.almaviva.eai.ljsa.sdk.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@ToString
@Data
@RequiredArgsConstructor
public class LjsaUserDTO {

     private final String username;
     private final Collection<? extends Serializable> roles;
     private final Map<String, ?> claims;
}
