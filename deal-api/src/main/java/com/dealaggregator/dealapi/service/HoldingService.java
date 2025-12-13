package com.dealaggregator.dealapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dealaggregator.dealapi.entity.Holding;
import com.dealaggregator.dealapi.repository.HoldingRepository;

/**
 * Service class for managing option contract holdings.
 *
 * Provides business logic for creating, retrieving, and deleting
 * user option positions (calls and puts).
 */
@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;

    /**
     * Constructor with dependency injection.
     *
     * @param holdingRepository Repository for database operations on holdings
     */
    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    /**
     * Adds a new option contract holding to the user's portfolio.
     *
     * @param userId Discord username of the user
     * @param ticker Stock ticker symbol (e.g., "NVDA")
     * @param type Option type - "CALL" or "PUT"
     * @param strike Strike price of the option
     * @param daysToEx Number of days until expiration
     * @param buyPrice Price paid for the contract
     * @return The saved Holding entity
     */
    public Holding addHolding(String userId, String ticker, String type, Double strike, int daysToEx, Double buyPrice) {
        // Calculate expiration date by adding days to current date
        LocalDate expirationDate = LocalDate.now().plusDays(daysToEx);

        // Create new holding with uppercase ticker and type for consistency
        Holding holding = new Holding(userId, ticker.toUpperCase(), type.toUpperCase(), strike, expirationDate, buyPrice);

        return holdingRepository.save(holding);
    }

    /**
     * Retrieves all holdings for a specific user.
     *
     * @param userId Discord username
     * @return List of all holdings for the user
     */
    public List<Holding> getHoldings(String userId) {
        return holdingRepository.findByDiscordUserId(userId);
    }

    /**
     * Retrieves a single holding by its database ID.
     *
     * @param id Database ID of the holding
     * @return Optional containing the holding if found
     */
    public Optional<Holding> getHoldingById(Long id) {
        return holdingRepository.findById(id);
    }

    /**
     * Removes a holding from the database by ID.
     *
     * @param id Database ID of the holding to remove
     */
    public void removeHolding(Long id) {
        holdingRepository.deleteById(id);
    }

    /**
     * Removes all holdings for a specific user and ticker symbol.
     *
     * Useful for closing all positions in a single stock.
     *
     * @param userId Discord username
     * @param ticker Stock ticker symbol
     * @return Number of holdings deleted
     */
    public int removeAllHoldingsByTickerAndUser(String userId, String ticker) {
        return holdingRepository.deleteByDiscordUserIdAndTicker(userId, ticker.toUpperCase());
    }
}