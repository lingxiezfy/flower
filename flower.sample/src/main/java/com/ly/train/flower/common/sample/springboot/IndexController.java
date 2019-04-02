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
package com.ly.train.flower.common.sample.springboot;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ly.flower.web.spring.FlowerController;
import com.ly.train.flower.common.annotation.Flower;
import com.ly.train.flower.common.service.container.ServiceFlow;

@RestController
@Flower(serviceName = "ServiceA", value = "async")
public class IndexController extends FlowerController {


  @RequestMapping(value = "/", method = POST)
  public void index(Object param, HttpServletRequest req) throws Throwable {
    doProcess(param, req);
  }


  @Override
  public void buildFlower() {
    ServiceFlow.getOrCreate(getFlowName()).buildFlow("ServiceA", "ServiceB");
  }

}
