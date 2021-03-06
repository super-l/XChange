package org.knowm.xchange.dto.marketdata;

import static org.knowm.xchange.dto.marketdata.Trades.TradeSortType.SortByID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * DTO representing a collection of trades
 * </p>
 */
public class Trades implements Serializable {

  private static final TradeIDComparator TRADE_ID_COMPARATOR = new TradeIDComparator();
  private static final TradeTimestampComparator TRADE_TIMESTAMP_COMPARATOR = new TradeTimestampComparator();

  private final List<Trade> trades;
  private final long lastID;
  private final TradeSortType tradeSortType;

  /**
   * Constructor
   * </p>
   * Default sort is SortByID
   *
   * @param trades List of trades
   */
  public Trades(List<Trade> trades) {

    this(trades, 0L, SortByID);
  }

  /**
   * Constructor
   *
   * @param trades List of trades
   * @param tradeSortType Trade sort type
   */
  public Trades(List<Trade> trades, TradeSortType tradeSortType) {

    this(trades, 0L, tradeSortType);
  }

  /**
   * Constructor
   *
   * @param trades A list of trades
   * @param lastID Last Unique ID
   * @param tradeSortType Trade sort type
   */
  public Trades(List<Trade> trades, long lastID, TradeSortType tradeSortType) {

    this.trades = new ArrayList<>(trades);
    this.lastID = lastID;
    this.tradeSortType = tradeSortType;

    switch (tradeSortType) {
      case SortByTimestamp:
        Collections.sort(this.trades, TRADE_TIMESTAMP_COMPARATOR);
        break;
      case SortByID:
        Collections.sort(this.trades, TRADE_ID_COMPARATOR);
        break;

      default:
        break;
    }
  }

  /**
   * @return A list of trades ordered by id
   */
  public List<Trade> getTrades() {

    return trades;
  }

  /**
   * @return a Unique ID for the fetched trades
   */
  public long getlastID() {

    return lastID;
  }

  public TradeSortType getTradeSortType() {

    return tradeSortType;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder("Trades\n");
    sb.append("lastID= ")
        .append(lastID)
        .append("\n");

    for (Trade trade : getTrades()) {
      sb.append("[trade=")
          .append(trade.toString())
          .append("]\n");
    }
    return sb.toString();
  }

  public enum TradeSortType {
    SortByTimestamp, SortByID
  }

  public static class TradeTimestampComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getTimestamp().compareTo(trade2.getTimestamp());
    }
  }

  public static class TradeIDComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getId().compareTo(trade2.getId());
    }
  }

}
