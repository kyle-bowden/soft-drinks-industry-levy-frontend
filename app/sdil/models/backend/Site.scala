/*
 * Copyright 2023 HM Revenue & Customs
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

package sdil.models.backend

import java.time.LocalDate
import play.api.libs.json.{Format, Json}
import sdil.models.{Address, Warehouse}

case class Site(
  address: UkAddress,
  ref: Option[String],
  tradingName: Option[String],
  closureDate: Option[LocalDate]
) {
  def getLines: List[String] =
    tradingName.fold(address.lines :+ address.postCode) { x =>
      (x :: address.lines) :+ address.postCode
    }
}

object Site {
  implicit val format: Format[Site] = Json.format[Site]

  def fromAddress(address: Address): Site =
    Site(UkAddress.fromAddress(address), None, None, None)

  def fromWarehouse(warehouse: Warehouse): Site =
    Site(UkAddress.fromAddress(warehouse.address), None, Some(warehouse.tradingName), None)
}
