package com.beyzataylan.bookify.repository;

import com.beyzataylan.bookify.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {

    List<Loans> findByBookId(Long id);


    List<Loans> findByUserUserId(Long userId);

    Optional<Loans> findByLoansConfirmationCode(String confirmationCode);
}

