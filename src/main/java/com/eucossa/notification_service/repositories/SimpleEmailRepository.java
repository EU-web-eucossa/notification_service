package com.eucossa.notification_service.repositories;

import com.eucossa.notification_service.entities.SimpleEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */
public interface SimpleEmailRepository extends JpaRepository<SimpleEmail, BigDecimal> {
}
