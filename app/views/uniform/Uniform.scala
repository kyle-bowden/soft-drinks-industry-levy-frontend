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

package views.uniform
import javax.inject.Inject

class Uniform @Inject()(
  val updateBusinessAddresses: views.html.uniform.fragments.update_business_addresses,
  // val ask: views.html.uniform.ask,
  // val cya: views.html.uniform.cya,
  // val end: views.html.uniform.end,
  // val journeyEnd: views.html.uniform.journeyEnd,
  // val many: views.html.uniform.many,
  // val tell: views.html.uniform.tell,
  val base: views.html.uniform.base
) {}
