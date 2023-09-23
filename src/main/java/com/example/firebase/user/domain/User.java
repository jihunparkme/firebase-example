package com.example.firebase.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private Date create_dt;
    private Date update_dt;
}
