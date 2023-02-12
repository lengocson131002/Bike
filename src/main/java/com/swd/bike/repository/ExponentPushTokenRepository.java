package com.swd.bike.repository;

import com.swd.bike.entity.ExponentPushToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExponentPushTokenRepository extends JpaRepository<ExponentPushToken, String> {

    @Query("SELECT DISTINCT e.token" +
            " FROM ExponentPushToken e" +
            " JOIN Account a" +
            " ON a.id = ?1")
    List<String> findAllTokensByAccountId(String accountId);

    @Query("SELECT DISTINCT e.token" +
            " FROM ExponentPushToken e" +
            " JOIN Account a" +
            " ON e.account = a")
    List<String> findAllTokens();
}