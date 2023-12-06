package com.cas735.finalproject.heartratemonitorsrv.business.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "app_user")
@NoArgsConstructor @AllArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    private @NotNull String username;

    private @NotNull int points;
}
