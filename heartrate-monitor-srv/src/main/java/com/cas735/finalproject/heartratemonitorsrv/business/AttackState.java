package com.cas735.finalproject.heartratemonitorsrv.business;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AttackState {
    private boolean ifBeingAttacked = false;

    public void setAttacked() {
        this.ifBeingAttacked = true;
    }

    public void setNotAttacked() {
        this.ifBeingAttacked = false;
    }

    public boolean isIfBeingAttacked() {
        return ifBeingAttacked;
    }
}
