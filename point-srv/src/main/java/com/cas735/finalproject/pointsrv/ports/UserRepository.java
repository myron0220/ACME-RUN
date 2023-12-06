package com.cas735.finalproject.pointsrv.ports;

import com.cas735.finalproject.pointsrv.business.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
