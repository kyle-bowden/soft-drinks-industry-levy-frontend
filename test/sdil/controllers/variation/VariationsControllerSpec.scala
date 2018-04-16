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

import java.time.LocalDate

import org.jsoup.Jsoup
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.BeforeAndAfterAll
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, status}
import sdil.controllers.ControllerSpec
import sdil.models.variations.VariationData
import uk.gov.hmrc.auth.core.{Enrolment, EnrolmentIdentifier, Enrolments}
import uk.gov.hmrc.auth.core.retrieve.Retrievals.allEnrolments
import sdil.controllers.ControllerSpec
import com.softwaremill.macwire._
import org.jsoup.Jsoup
import org.mockito.ArgumentMatchers.{eq => matching, _}
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterAll
import play.api.i18n.Messages
import play.api.test.FakeRequest
import play.api.test.Helpers._
import sdil.models.Address
import sdil.models.backend.{Contact, UkAddress}
import sdil.models.retrieved.{RetrievedActivity, RetrievedSubscription}
import sdil.models.variations.{UpdatedBusinessDetails, VariationData}
import uk.gov.hmrc.auth.core.{Enrolment, EnrolmentIdentifier, Enrolments}
import uk.gov.hmrc.auth.core.retrieve.Retrievals.allEnrolments

import scala.collection.JavaConverters._
import scala.concurrent.Future

class VariationsControllerSpec extends ControllerSpec with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    val sdilEnrolment = EnrolmentIdentifier("EtmpRegistrationNumber", "XZSDIL000100107")

    when(mockAuthConnector.authorise[Enrolments](any(), matching(allEnrolments))(any(), any())).thenReturn {
      Future.successful(Enrolments(Set(Enrolment("HMRC-OBTDS-ORG", Seq(sdilEnrolment), "Active"))))
    }

    when(mockKeystore.fetchAndGetEntry[VariationData](matching("variationData"))(any(), any(), any()))
      .thenReturn(Future.successful(Some(VariationData(subscription))))
  }

  "GET /variations/business-details" should {
    "return 200 Ok and the business details page" in {
      val res = testController.show()(FakeRequest())
      status(res) mustBe OK

      contentAsString(res) must include(messagesApi("sdil.variation.heading"))
    }
  }
  lazy val testController = wire[VariationsController]
}
