/**
 * Copyright © 2019 同程艺龙 (zhihui.li@ly.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ly.train.flower.common.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import com.ly.train.flower.common.annotation.Scope;
import com.ly.train.flower.common.serializer.Codec;
import com.ly.train.flower.common.service.Aggregate;
import com.ly.train.flower.common.service.Service;
import com.ly.train.flower.common.service.container.ServiceContext;
import com.ly.train.flower.common.service.message.FlowMessage;
import com.ly.train.flower.common.util.Assert;
import com.ly.train.flower.common.util.Constant;
import com.ly.train.flower.common.util.cache.Cache;
import com.ly.train.flower.common.util.cache.CacheManager;
import com.ly.train.flower.logging.Logger;
import com.ly.train.flower.logging.LoggerFactory;

@Scope(scopeName = Constant.SCOPE_REQUEST)
public class AggregateService implements Service<Object, List<Object>>, Aggregate {
  static final Logger logger = LoggerFactory.getLogger(AggregateService.class);
  private static final Long DefaultTimeOutMilliseconds = 60000L;

  private int sourceNumber = 0;
  private Long timeoutMillis = DefaultTimeOutMilliseconds;

  private static final String cacheKeyPrefix = "FLOWER_AGGREGATE_SERVICE_";
  private ReentrantLock lock = new ReentrantLock();

  public AggregateService() {}

  public AggregateService(String config) {
    this.timeoutMillis = Long.valueOf(config);
  }

  @Override
  public List<Object> process(Object message, ServiceContext context) {
    FlowMessage flowMessage = context.getFlowMessage();

    final String transactionId = flowMessage.getTransactionId();
    AggregateInfo aggregateInfo = getServiceInfo(context.getFlowName(), transactionId);
    aggregateInfo.addResult(flowMessage);
    if (aggregateInfo.getResultNum().decrementAndGet() <= 0) {
      return buildMessage(context.getFlowName(), transactionId);
    }
    return null;
  }

  private void clear(final String flowName, final String transactionId) {
    Assert.notNull(flowName, "flowName can't be null .");
    CacheManager.get(cacheKeyPrefix + flowName).invalidate(transactionId);
  }

  private AggregateInfo getServiceInfo(final String flowName, final String transactionId) {
    Assert.notNull(flowName, "flowName can't be null .");
    CacheManager cacheManager = CacheManager.get(cacheKeyPrefix + flowName);
    Cache<AggregateInfo> cache = cacheManager.getCache(transactionId);
    if (cache == null) {
      lock.lock();
      try {
        cache = cacheManager.getCache(transactionId);
        if (cache == null) {
          AggregateInfo aggregateInfo = new AggregateInfo(transactionId, sourceNumber);
          Cache<AggregateInfo> temp = cacheManager.add(transactionId, aggregateInfo, timeoutMillis);
          if (temp != null) {
            cache = temp;
          } else {
            cache = cacheManager.getCache(transactionId);
          }
        }
      } finally {
        lock.unlock();
      }
    }
    AggregateInfo aggregateInfo = cache.getValue();
    return aggregateInfo;
  }

  /**
   * subclass should override the method.
   * 
   * @param messages Set<Message>
   * @return Object
   */
  public List<Object> buildMessage(String flowName, String transactionId) {
    List<Object> ret = new ArrayList<Object>();
    try {
      AggregateInfo aggregateInfo = getServiceInfo(flowName, transactionId);
      List<FlowMessage> messages = aggregateInfo.getResults();
      for (FlowMessage message : messages) {
        ret.add(Codec.Hessian.decode(message.getMessage(), message.getMessageType()));
      }
    } finally {
      clear(flowName, transactionId);
    }
    return ret;
  }

  // sourceNumber++ when initialize
  public void setSourceNumber(int sourceNumber) {
    this.sourceNumber = sourceNumber;
  }


  // cahce object
  class AggregateInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long createTime = System.currentTimeMillis();
    private final String id;
    private List<FlowMessage> results;
    private AtomicInteger resultNum;

    public AggregateInfo(String id, int resultNum) {
      Assert.notNull(id, "cacheKey can't be null.");
      this.id = id;
      this.resultNum = new AtomicInteger(resultNum);
    }

    public AggregateInfo addResult(FlowMessage result) {
      if (results == null) {
        results = new ArrayList<>();
      }
      results.add(result);
      return this;
    }

    public List<FlowMessage> getResults() {
      return results;
    }

    public AtomicInteger getResultNum() {
      return resultNum;
    }

    public String getId() {
      return id;
    }

    public boolean isTimeout() {
      return System.currentTimeMillis() - createTime > AggregateService.this.timeoutMillis;
    }

  }
}
