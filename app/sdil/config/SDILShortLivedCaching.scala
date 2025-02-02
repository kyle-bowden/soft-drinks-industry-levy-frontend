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

package sdil.config

import play.api.{Configuration, Environment}
import uk.gov.hmrc.http.HttpClient
import uk.gov.hmrc.http.cache.client.ShortLivedHttpCaching
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.Inject

class SDILShortLivedCaching @Inject()(val http: HttpClient, val configuration: Configuration, environment: Environment)
    extends ServicesConfig(configuration) with ShortLivedHttpCaching {

  override def defaultSource: String = configuration.get[String]("appName")

  override def baseUri: String = baseUrl("cacheable.short-lived-cache")

  override def domain: String =
    getConfString(
      "cacheable.short-lived-cache.domain",
      throw new Exception("Missing config cacheable.short-lived-cache.domain"))
}
