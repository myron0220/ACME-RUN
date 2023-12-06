package com.cas735.finalproject.attackgenerationsrv.business.entities;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Attack {
    private @NonNull String enemyName;
    private @NonNull int score;
    private @NonNull String option1 = "Sheltering";
    private @NonNull String option2 = "Run";
    private @NonNull String option3 = "Fight";
}
