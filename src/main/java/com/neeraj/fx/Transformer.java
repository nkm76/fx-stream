package com.neeraj.fx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

/**
 * Defines transformer for converting string messages to FxRate record.
 */
public interface Transformer {
    Logger logger = LoggerFactory.getLogger(Transformer.class);
    String COMMA_DELIMITER = ",";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");

    static Collection<FxRate> toFxRates(String lines) {
        Collection<FxRate> rates = new ArrayList<>();
        try (Scanner linesScanner = new Scanner(lines)) {
            while (linesScanner.hasNextLine()) {
                toFxRate(linesScanner.nextLine()).map(rates::add);
            }
        }
        return rates;
    }

    private static Optional<FxRate> toFxRate(String line) {
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            Long id = null;
            String ccyPair = null;
            BigDecimal bidPrice = null;
            BigDecimal askPrice = null;
            LocalDateTime timestamp = null;
            if (rowScanner.hasNext()) {
                id = Long.parseLong(rowScanner.next());
            }
            if (rowScanner.hasNext()) {
                ccyPair = rowScanner.next().strip();
            }
            if (rowScanner.hasNext()) {
                bidPrice = new BigDecimal(rowScanner.next().strip());
            }
            if (rowScanner.hasNext()) {
                askPrice = new BigDecimal(rowScanner.next().strip());
            }
            if (rowScanner.hasNext()) {
                timestamp = LocalDateTime.parse(rowScanner.next().strip(), DATE_TIME_FORMATTER);
            }
            return Optional.of(new FxRate(id, ccyPair, bidPrice, askPrice, timestamp));
        } catch (Exception ex) {
            // Log and ignore bad data
            logger.warn("Could not parse line: {} {}", line, ex.getMessage());
            return Optional.empty();
        }
    }


}
