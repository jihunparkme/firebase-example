package com.example.firebase.user.domain;

import com.google.cloud.Timestamp;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private Timestamp create_dt;
    private Timestamp update_dt;
}
