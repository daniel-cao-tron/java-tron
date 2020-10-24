package org.tron.core.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j(topic = "DB")
@Component
public class NewTransactionStore extends TransactionStore {

  @Autowired
  private NewTransactionStore(@Value("new-trans") String dbName) {
    super(dbName);
  }
}