package com.minkyaw.banking.repository;

import com.minkyaw.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
