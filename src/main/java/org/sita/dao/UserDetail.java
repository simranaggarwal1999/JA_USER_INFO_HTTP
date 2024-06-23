package org.sita.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetail {
    private String user;
    private String workstation;
    private String status;
    private String message;
}
