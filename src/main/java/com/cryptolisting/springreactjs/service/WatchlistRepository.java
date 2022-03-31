package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.UserWatchlist;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<UserWatchlist, Integer> {
    @NonNull Optional<UserWatchlist> findById(@NonNull Integer id);
}
