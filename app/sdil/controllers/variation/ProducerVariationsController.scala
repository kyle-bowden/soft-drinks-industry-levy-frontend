/*
 * Copyright 2018 HM Revenue & Customs
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

package sdil.controllers.variation

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import sdil.actions.VariationAction
import sdil.config.AppConfig
import sdil.controllers.ProducerController
import uk.gov.hmrc.http.cache.client.SessionCache
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.softdrinksindustrylevy.register.produce_worldwide

import scala.concurrent.Future

class ProducerVariationsController(val messagesApi: MessagesApi,
                                   cache: SessionCache,
                                   variationAction: VariationAction)
                                  (implicit config: AppConfig)
  extends FrontendController with I18nSupport {

  def show: Action[AnyContent] = variationAction { implicit request =>
    Ok(produce_worldwide(ProducerController.form.fill(request.data.producer), backLink, submitAction))
  }

  def submit: Action[AnyContent] = variationAction.async { implicit request =>
    ProducerController.form.bindFromRequest().fold(
      errors => Future.successful(BadRequest(produce_worldwide(errors, backLink, submitAction))),
      data => {
        val updated = request.data.copy(producer = data)
        cache.cache("variationData", updated) map { _ =>
          if (data.isLarge.contains(false)) {
            Redirect(routes.UsesCopackerController.show())
          } else if (data.isProducer) {
            Redirect(routes.PackageOwnController.show())
          } else {
            Redirect(routes.VariationsController.show())
          }
        }
      }
    )
  }

  lazy val backLink = routes.VariationsController.show()

  lazy val submitAction = routes.ProducerVariationsController.submit()
}

