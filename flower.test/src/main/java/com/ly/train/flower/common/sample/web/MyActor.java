package com.ly.train.flower.common.sample.web;

import java.util.Date;

import com.ly.train.flower.common.service.FlowContext;
import com.ly.train.flower.common.service.web.Web;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends AbstractActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(
            String.class,
            s -> {
              log.info("Received String message: {}", s);
              
              Web web = FlowContext.getServiceContext(s).getWeb();
              web.println("MyActor处理完毕的时间：" + new Date() + ".");
              web.flush();
              web.complete();
              
              // #my-actor
              // #reply
//              getSender().tell(s, getSelf());
              // #reply
              // #my-actor
            })
        .matchAny(o -> log.info("received unknown message"))
        .build();
  }
}