package com.ly.train.flower.common.sample.web;

import java.io.PrintWriter;
import java.util.Date;

import com.ly.train.flower.common.service.FlowContext;
import com.ly.train.flower.common.service.web.Web;

public class Executor implements Runnable {
  private Web web = null;

  public Executor(String uuid) {
    this.web = FlowContext.getServiceContext(uuid).getWeb();
  }

  public void run() {
    try {
      Thread.sleep(5000);
      // PrintWriter out = ctx.getResponse().getWriter();
      web.println("业务处理完毕的时间：" + new Date() + ".");
      web.flush();
      web.complete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
