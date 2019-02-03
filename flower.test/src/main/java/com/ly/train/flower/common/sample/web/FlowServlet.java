package com.ly.train.flower.common.sample.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.train.flower.common.actor.ServiceActor;
import com.ly.train.flower.common.actor.ServiceFacade;
import com.ly.train.flower.common.service.FlowContext;
import com.ly.train.flower.common.service.ServiceContext;
import com.ly.train.flower.common.service.ServiceFactory;
import com.ly.train.flower.common.service.ServiceFlow;
import com.ly.train.flower.common.service.web.Web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@WebServlet(asyncSupported = true)
public class FlowServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();
    out.println("进入Servlet的时间：" + new Date() + ".");
    out.flush();

    AsyncContext ctx = req.startAsync();

    // flower
//    asyncExe(ctx);

    // thread
    ServiceContext serviceContext = new ServiceContext();
    Web web = new Web(ctx);
    serviceContext.setWeb(web);
    String uuid = UUID.randomUUID().toString();
    FlowContext.putServiceContext(uuid, serviceContext);
//    new Thread(new Executor(uuid)).start();
    
    
    ActorRef actor = ActorSystem.create("sample").actorOf(Props.create(MyActor.class));
    actor.tell(uuid, null);

    out.println("结束Servlet的时间：" + new Date() + ".");
    out.flush();
  }

  private void asyncExe(AsyncContext ctx) {
    buildServiceEnv();
    try {
      ServiceFacade.asyncCallService("flow", "flowService", " Hello World! ", ctx);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ctx.complete();
  }

  private void buildServiceEnv() {
    ServiceFactory.registerService("flowService",
        "com.ly.train.flower.common.sample.web.FlowService");
    ServiceFactory.registerService("endService",
        "com.ly.train.flower.common.service.NothingService");

    // serviceA -> serviceB -> serviceC
    ServiceFlow.buildFlow("flower", "flowService", "endService");

  }
}
